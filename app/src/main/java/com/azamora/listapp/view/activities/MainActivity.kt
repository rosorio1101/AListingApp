package com.azamora.listapp.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.azamora.listapp.R
import com.azamora.listapp.action
import com.azamora.listapp.data.model.LaptopModel
import com.azamora.listapp.snack
import com.azamora.listapp.view.adapter.LaptopsAdapter
import com.azamora.listapp.viewmodel.LaptopViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_app_main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LaptopViewModel

    private var adapter = LaptopsAdapter(mutableListOf()) { laptop -> displayDescription(laptop) }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProviders.of(this).get(LaptopViewModel::class.java)
        bringData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_app_main_activity)
        laptopsRecyclerView.adapter = adapter

    }

    fun bringData() {
        showLoading()
        viewModel.getLatopsList().observe(this, Observer { laptops ->
            hideLoading()
            when(laptops) {
                null -> showMessage()
                else -> adapter.setLaptops(laptops)
            }
        })
    }

    fun displayDescription(laptop: LaptopModel) {
        // TODO:
    }

    private fun showMessage() {
        listLayout.snack(getString(R.string.list_app_network_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.list_app_ok)) {
                bringData()
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        laptopsRecyclerView.isEnabled = false
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        laptopsRecyclerView.isEnabled = true
    }
}