package com.droal.testproximate

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.droal.testproximate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.listFragment), binding.drawerLayout)

        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController!!, appBarConfiguration!!)

        getSupportActionBar()?.setIcon(R.drawable.icon)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if(sharedPref != null){
            with (sharedPref.edit()) {
                putBoolean("sesion", false)
                putString("user", "")
                putString("password", "")
                apply()
            }
        }

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}