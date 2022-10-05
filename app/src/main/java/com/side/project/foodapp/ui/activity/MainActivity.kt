package com.side.project.foodapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.side.project.foodapp.R
import com.side.project.foodapp.databinding.ActivityMainBinding
import com.side.project.foodapp.ui.AnimManager
import org.koin.android.ext.android.inject

// https://ithelp.ithome.com.tw/articles/10225937
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val animManager: AnimManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        init()
    }

    private fun init() {
        val bottomNavigationView: BottomNavigationView = mainBinding.bottomNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    fun toggleNavHost(isCollect: Boolean) {
        mainBinding.run {
            if (isCollect)
                bottomNav.apply {
                    visibility = View.INVISIBLE
                    menu.forEach { it.isEnabled = false }
                    startAnimation(animManager.toBottom)
                }
            else
                bottomNav.apply {
                    visibility = View.VISIBLE
                    menu.forEach { it.isEnabled = true }
                    startAnimation(animManager.fromBottom)
                }
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level <= TRIM_MEMORY_BACKGROUND)
            System.gc()
    }
}