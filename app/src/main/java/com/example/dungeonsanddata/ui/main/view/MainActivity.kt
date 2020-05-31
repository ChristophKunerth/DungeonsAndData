package com.example.dungeonsanddata.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dungeonsanddata.R
import com.example.dungeonsanddata.data.api.ApiHelper
import com.example.dungeonsanddata.data.api.ApiImplementation
import com.example.dungeonsanddata.data.model.Player
import com.example.dungeonsanddata.ui.base.ViewModelFactory
import com.example.dungeonsanddata.ui.main.adapter.MainAdapter
import com.example.dungeonsanddata.ui.main.viewmodel.MainViewModel
import com.example.dungeonsanddata.utils.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupViewModel()
        setupObserver()

        val bottomNavigationView = findViewById<ChipNavigationBar>(R.id.nav_bar)
        bottomNavigationView.setItemSelected(R.id.action_players)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item) {
                R.id.action_players -> Log.d("NAVBAR_CLICK", "Clicked on Players in the Player Activity")

                R.id.action_spells -> {
                    intent = Intent(this, SpellListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    bottomNavigationView.setItemSelected(R.id.action_players)
                }

                R.id.action_items -> {
                    intent = Intent(this, ItemActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                    bottomNavigationView.setItemSelected(R.id.action_players)
                }
            }
        }
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(ApiHelper(ApiImplementation()))
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        mainViewModel.getPlayers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { players -> renderList(players) }
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

    private fun renderList(players: List<Player>) {
        adapter.addData(players)
        adapter.notifyDataSetChanged()
    }
}
