package com.example.lab03

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab03.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var bottomNav: BottomNavigationView
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        applyTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.appbarMain.visibility = View.GONE

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController =navHostFragment.navController
        bottomNav = binding.mainNavigation

        setSupportActionBar(binding.toolbar)

        val appBarConfig : AppBarConfiguration =
            AppBarConfiguration(setOf(R.id.leftLayout,R.id.fragmentCenter,R.id.rightLayout))
        setupActionBarWithNavController(navController,appBarConfig)
        bottomNav.setupWithNavController(navController)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.nav_menu, menu)
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        if(menu is MenuBuilder) {
            //noinspection RestrictedApi
            menu.setOptionalIconsVisible(true)
        }

        menuInflater.inflate(R.menu.startmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.menuReset -> {
                setPrefs(0)
                editor.putBoolean("isBold", false)
                editor.putBoolean("isItalic", false)
                editor.putFloat("fontSize", 14f)
                editor.apply()
                recreate()
                true
            }

            R.id.menuFirst -> {
                setPrefs(1)
                recreate()
                true
            }

            R.id.menuSecond -> {
                setPrefs(2)
                recreate()
                true
            }

            R.id.menuThird -> {
                setPrefs(3)
                recreate()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun setPrefs(themeNum: Int) {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val editor = data.edit()
        editor.putInt("theme_num", themeNum)
        editor.apply()
    }

    private fun applyTheme() {
        val data: SharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val themeNum = data.getInt("theme_num", 0)
        when(themeNum) {
            1 -> setTheme(R.style.AppTheme1)
            2 -> setTheme(R.style.Apptheme2)
            3 -> setTheme(R.style.Apptheme3)
            else -> setTheme(R.style.AppTheme)
        }
    }
}