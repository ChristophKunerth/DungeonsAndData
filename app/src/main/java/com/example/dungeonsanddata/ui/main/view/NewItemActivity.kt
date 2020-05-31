package com.example.dungeonsanddata.ui.main.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.dungeonsanddata.R
import java.lang.StringBuilder

class NewItemActivity : AppCompatActivity() {

    private lateinit var editNameView: EditText
    private lateinit var editDescriptionView: EditText
    private lateinit var editValueView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_item_activity)

        editNameView = findViewById(R.id.edit_name)
        editDescriptionView = findViewById(R.id.edit_description)
        editValueView = findViewById(R.id.edit_value)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val intent = Intent()
            if(TextUtils.isEmpty(editNameView.text) || TextUtils.isEmpty(editValueView.text) || TextUtils.isEmpty(editDescriptionView.text)) {
                setResult(Activity.RESULT_CANCELED, intent)
            }
            else {
                val name = editNameView.text.toString()
                val description = editDescriptionView.text.toString()
                val value = editValueView.text.toString()

                intent.putExtra("ITEMNAME", name)
                intent.putExtra("ITEMDESC", description)
                intent.putExtra("ITEMVAL", value)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}
