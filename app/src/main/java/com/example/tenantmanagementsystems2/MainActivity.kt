package com.example.tenantmanagementsystems2

import com.example.tenantmanagementsystems2.databinding.NavHeaderBinding
import androidx.navigation.ui.setupWithNavController
import android.content.Intent
import android.view.MenuItem
import androidx.navigation.ui.onNavDestinationSelected
import android.view.Menu
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tenantmanagementsystems2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // The "driver" that switches Fragments inside the NavHost
    private lateinit var navController: NavController

    // Tells the App Bar which screens are top-level (no back arrow)
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "MainActivity: onCreate")

        // Create the views from activity_main.xml (Toolbar + NavHost)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 1. Use our Toolbar as the App Bar
        //    toolbar  ←  android:id="@+id/toolbar" in activity_main.xml
        setSupportActionBar(binding.toolbar)

        // 2. Get the NavController from the NavHost
        //    nav_host_fragment  ←  android:id="@+id/nav_host_fragment"
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        // 3. Connect the App Bar to navigation:
        //    the title comes from each destination's android:label
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dashboardFragment, R.id.addTenantFragment, R.id.viewTenantsFragment),
            binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        // Our own listener: it REPLACES the one setupWithNavController just installed,
        // so we must handle navigation and closing the drawer ourselves.
        binding.navView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.action_logout) {
                // Logout is not a screen in the nav graph, so use an explicit Intent
                val intent = Intent(this, LoginActivity::class.java)
                // Clear every open screen so Back from Login can't return to the app
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            } else {
                // Everything else: navigate by matching IDs, then close the drawer
                val handled = item.onNavDestinationSelected(navController)
                binding.drawerLayout.closeDrawers()
                handled
            }
        }

        val email = intent.getStringExtra("EMAIL")

        val headerBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0))

        headerBinding.headerEmailTextView.text = email ?: "Signed in"
    }

    // 4. Make the App Bar's back arrow work
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    // Turn main_menu.xml into menu items in the App Bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    // What happens when a menu item is tapped
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_logout) {

            val intent = Intent(this, LoginActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            return true
        }

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    // ── LIFECYCLE METHODS ─────────────────────────────────────────
    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "MainActivity: onDestroy")
    }
}   // ← the class closes HERE, the only brace at column 0