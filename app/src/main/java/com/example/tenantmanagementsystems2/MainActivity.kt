package com.example.tenantmanagementsystems2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystems2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastTenant: Tenant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Exercise 2: read the email sent from LoginActivity
        val loggedInEmail = intent.getStringExtra("EMAIL")
        if (loggedInEmail != null && savedInstanceState == null) {
            Toast.makeText(this, "Logged in as $loggedInEmail", Toast.LENGTH_SHORT).show()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.tenantNameEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val rent = binding.rentEditText.text.toString().trim()

            // Exercise 3: validate all three fields
            var valid = true
            if (name.isEmpty()) {
                binding.tenantNameEditText.error = "Required"
                valid = false
            }
            if (phone.isEmpty()) {
                binding.phoneEditText.error = "Required"
                valid = false
            }
            if (rent.isEmpty()) {
                binding.rentEditText.error = "Required"
                valid = false
            }
            if (!valid) return@setOnClickListener

            val tenant = Tenant(name, phone, rent)
            binding.tenant = tenant
            lastTenant = tenant
        }

        binding.callButton.setOnClickListener {
            val tenant = lastTenant
            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${tenant.phone}"))
            startActivity(dialIntent)
        }

        // Exercise 1: share the saved tenant
        binding.shareButton.setOnClickListener {
            val tenant = lastTenant
            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, tenant.summary())
            startActivity(Intent.createChooser(shareIntent, "Share tenant"))
        }
    }
}