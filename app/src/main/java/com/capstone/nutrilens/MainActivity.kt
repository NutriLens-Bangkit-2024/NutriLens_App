package com.capstone.nutrilens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.nutrilens.data.util.Preferences
import com.capstone.nutrilens.data.util.getImageUri
import com.capstone.nutrilens.databinding.ActivityMainBinding
import com.capstone.nutrilens.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null
    private lateinit var preferences: Preferences

    private fun allPermissionGranted()=
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        startActivity(Intent(this,LoginActivity::class.java))
        preferences = Preferences(this)

        if(!allPermissionGranted()){
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_recipe, R.id.navigation_progress,R.id.navigation_profile,R.id.navigation_scanner
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when (destination.id) {
                R.id.navigation_scanner -> navView.visibility = View.GONE
                else->navView.visibility = View.VISIBLE
            }
        }

    }

//    private fun startCamera() {
//        currentImageUri = getImageUri(this)
//        launcherIntentCamera.launch(currentImageUri!!)
//    }
//
//    private val launcherIntentCamera = registerForActivityResult(
//        ActivityResultContracts.TakePicture()
//    ) { isSuccess ->
//        if (isSuccess) {
//            showImage()
//        }
//    }
//
//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image URI", "showImage: $it")
////            binding.ivContoh.setImageURI(it)
//        }
//    }

    companion object{
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}