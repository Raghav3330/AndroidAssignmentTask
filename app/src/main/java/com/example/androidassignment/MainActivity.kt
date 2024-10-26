package com.example.androidassignment

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.androidassignment.databinding.ActivityMainBinding
import com.example.androidassignment.presentation.viewmodel.ArticleViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this) {
            handleBackPress()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        INSTANCE = this

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.homeFragment       -> openHome()
                R.id.favouriteFragment  -> openFavourites()
                R.id.orderFragment      -> openOrders()
                R.id.settingsFragment   -> openSettings()
            }
            true
        }
    }

    private fun handleBackPress() {
        when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                finish()
            }
            else -> {
                navController.popBackStack()
            }
        }
    }

    // Get the view model
    val viewModel by viewModels<ArticleViewModel>(
        factoryProducer = { Injection.provideViewModelFactory(owner = this) }
    )

    companion object{
        var INSTANCE: MainActivity? = null
    }

    fun openHome(){
        navController.navigate(R.id.homeFragment)
    }

    fun openFavourites(){
        navController.navigate(R.id.favouriteFragment)
    }
    fun openOrders(){
        navController.navigate(R.id.orderFragment)
    }
    fun openSettings(){
        navController.navigate(R.id.settingsFragment)
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }

}