package com.example.dungeonsanddata.ui.main.view

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
import com.example.dungeonsanddata.R
import com.example.dungeonsanddata.data.api.ApiHelper
import com.example.dungeonsanddata.data.api.ApiImplementation
import com.example.dungeonsanddata.data.model.Spell
import com.example.dungeonsanddata.ui.base.ViewModelFactory
import com.example.dungeonsanddata.ui.main.adapter.SpellListAdapter
import com.example.dungeonsanddata.ui.main.viewmodel.SpellListViewModel
import com.example.dungeonsanddata.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class SpellListActivity : AppCompatActivity() {
    private lateinit var spellListViewModel: SpellListViewModel
    private lateinit var adapter: SpellListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.spell_list_activity)

        setupUI()
        setupViewModel()
        setupObserver()

        val bottomNavigationView = findViewById<ChipNavigationBar>(R.id.nav_bar)
        bottomNavigationView.setItemSelected(R.id.action_spells)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item) {
                R.id.action_players -> {
                    intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    bottomNavigationView.setItemSelected(R.id.action_spells)
                }

                R.id.action_spells -> Log.d("NAVBAR_CLICK", "Clicked on Spells in the Spells Activity")

                R.id.action_items -> {
                    intent = Intent(this, ItemActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    bottomNavigationView.setItemSelected(R.id.action_spells)
                }
            }
        }
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SpellListAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        spellListViewModel = ViewModelProvider(
            this, ViewModelFactory(ApiHelper(ApiImplementation()))
        ).get(SpellListViewModel::class.java)
    }

    private fun setupObserver() {
        spellListViewModel.getSpells().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { spells -> renderList(spells) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(spells: List<Spell>) {
        adapter.addData(spells)
        adapter.notifyDataSetChanged()
    }
}
