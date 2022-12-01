package com.technifutur.neopixl.evalandfinale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.technifutur.neopixl.evalandfinale.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = ""

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            when(destination.id){
                R.id.searchMovieFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.topRatedMoviesFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.detailMovieFragment -> binding.bottomNavigationView.visibility = View.GONE
            }
        }
        var bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView,navController)
    }
}