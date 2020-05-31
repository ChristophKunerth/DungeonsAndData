package com.example.dungeonsanddata.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dungeonsanddata.R
import com.example.dungeonsanddata.data.api.ApiHelper
import com.example.dungeonsanddata.data.api.ApiImplementation
import com.example.dungeonsanddata.data.model.Item
import com.example.dungeonsanddata.data.model.Spell
import com.example.dungeonsanddata.ui.base.ViewModelFactory
import com.example.dungeonsanddata.ui.main.adapter.ItemListAdapter
import com.example.dungeonsanddata.ui.main.adapter.SpellListAdapter
import com.example.dungeonsanddata.ui.main.viewmodel.ItemViewModel
import com.example.dungeonsanddata.ui.main.viewmodel.SpellListViewModel
import com.example.dungeonsanddata.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi

class ItemActivity : AppCompatActivity() {

    @InternalCoroutinesApi
    private lateinit var itemViewModel: ItemViewModel
    private val addItemRequestCode = 1

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ItemListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        // Observe the data and update our List
        itemViewModel.allItems.observe(this, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.ActionButton)
        fab.setOnClickListener {
            val intent = Intent(this@ItemActivity, NewItemActivity::class.java)
            startActivityForResult(intent, addItemRequestCode)
        }

        val bottomNavigationView = findViewById<ChipNavigationBar>(R.id.nav_bar)
        bottomNavigationView.setItemSelected(R.id.action_items)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item) {
                R.id.action_players -> {
                    intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    bottomNavigationView.setItemSelected(R.id.action_items)
                }

                R.id.action_spells -> {
                    intent = Intent(this, SpellListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    bottomNavigationView.setItemSelected(R.id.action_items)
                }

                R.id.action_items -> {Log.d("NAVBAR_CLICK", "Clicked on Players in the Item Activity")}
            }
        }
    }

    @InternalCoroutinesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val name: String?
        val description: String?
        val value: String?

        if (requestCode == addItemRequestCode && resultCode == Activity.RESULT_OK) {
            name = data?.getStringExtra("ITEMNAME")
            description = data?.getStringExtra("ITEMDESC")
            value = data?.getStringExtra("ITEMVAL")

            itemViewModel.insert(Item(name!!, value!!, description!!))
        }
        else {
            Toast.makeText(
                applicationContext,
                "Error: Field(s) left blank. Please fill out all the Fields",
                Toast.LENGTH_LONG).show()
        }
    }
}
