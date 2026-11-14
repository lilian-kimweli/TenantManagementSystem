package com.example.tenantmanagementsystems2

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tenantmanagementsystems2.databinding.FragmentAddTenantBinding
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class AddTenantFragment : Fragment() {

    private var _binding: FragmentAddTenantBinding? = null
    private val binding get() = _binding!!

    // NEW (Task 1): the Fragment OBJECT is created (no view yet)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onCreateView")  // NEW
        _binding = FragmentAddTenantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onViewCreated")  // NEW

        // saveButton  ←  android:id="@+id/saveButton" in fragment_add_tenant.xml
        binding.saveButton.setOnClickListener {

            // Read the three EditTexts in fragment_add_tenant.xml
            val name = binding.tenantNameEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val rent = binding.rentEditText.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || rent.isEmpty()) {
                // A Fragment is not a Context, so ask for its Activity's
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // NEW (Task 2): count only digits, so "0712 345 678" still counts as 10
            if (phone.count { it.isDigit() } < 10) {
                binding.phoneEditText.error = "Enter a valid phone number"
                return@setOnClickListener   // stop here: don't save, don't navigate
            }

            val tenant = Tenant(name, phone, rent)

            // Add to the shared list (the Dashboard and View Tenants read it)
            TenantRepository.addTenant(tenant)

            // SAFE ARGS: this function was generated from the <action> in
            // nav_graph.xml. Because View Tenants has a <argument> of type
            // Tenant, the function REQUIRES a Tenant: it won't compile without one
            val action = AddTenantFragmentDirections
                .actionAddTenantFragmentToViewTenantsFragment(tenant)

            // Ask the NavController to follow the action
            findNavController().navigate(action)
        }
    }

    // NEW (Task 1): visible
    override fun onStart() {
        super.onStart()
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onStart")
    }

    // NEW (Task 1): in front, user can interact
    override fun onResume() {
        super.onResume()
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onResume")
    }

    // NEW (Task 1): leaving the front
    override fun onPause() {
        super.onPause()
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onPause")
    }

    // NEW (Task 1): no longer visible
    override fun onStop() {
        super.onStop()
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onDestroyView")  // NEW
        _binding = null
    }

    // NEW (Task 1): the Fragment OBJECT is destroyed
    override fun onDestroy() {
        super.onDestroy()
        Log.d("TENANT_LIFECYCLE", "  AddTenantFragment: onDestroy")
    }
}