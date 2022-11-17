package ru.effectivemobile.ui.activity

import android.app.ActivityOptions
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.effectivemobile.R
import ru.effectivemobile.databinding.ActivityMainBinding
import ru.effectivemobile.ui.navigation.setMainNavigationGraph
import ru.effectivemobile.utils.fastlog


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        hideSplashScreen()

        setupView()

        setupNavigation()


    }

    private fun hideSplashScreen() {
        window?.setBackgroundDrawableResource(R.color.background)
        window?.navigationBarColor = resources.getColor(R.color.background,null)
    }

    private fun setupView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        navController = binding.navHost.getFragment<NavHostFragment>().navController

        navController.setMainNavigationGraph()

    }

}

