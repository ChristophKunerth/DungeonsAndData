package com.example.dungeonsanddata.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dungeonsanddata.data.model.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch

// TODO: Set exportSchema to true and create directory to export Schema to. (Read about Version Control and Migration Strategies)
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class DungeonsAndDatabase : RoomDatabase() {

    abstract fun itemDAO(): ItemDAO

    private class ItemDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var itemDAO = database.itemDAO()

                    // Empty DB on start
                    //itemDAO.deleteAllItems()

                    //Free Items!
                    var item = Item("Gauntlet of Pain", "2g 15s", "A magnificent Gauntlet able to mince your Opponents")
                    itemDAO.insert(item)
                    item = Item("Shield of Courage", "79s", "This Shield can block even magic and imbues the wielder with otherworldly confidence!")
                    itemDAO.insert(item)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DungeonsAndDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context, scope: CoroutineScope): DungeonsAndDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DungeonsAndDatabase::class.java,
                    "item_database"
                )
                .addCallback(ItemDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}