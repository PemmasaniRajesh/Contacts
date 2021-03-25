package rajeshfsd.contacts

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import rajeshfsd.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        initGUI()
    }

    private fun initGUI(){

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController  = navHostFragment.navController

        binding.bnvHome.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.allcontacts -> {
                    navController.popBackStack(R.id.favourite_contacts_fragment,true)
                    navController.navigate(R.id.all_contacts_fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favourites -> {
                    navController.popBackStack(R.id.all_contacts_fragment,true)
                    navController.navigate(R.id.favourite_contacts_fragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        binding.fabCreate.setOnClickListener {
            navController.navigate(R.id.contact_Fragment)
        }
    }

    private fun checkPermissions(){

        // Initialize a list of required permissions to request runtime
        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if(ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.CAMERA), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    list.toTypedArray(), 1)
            }
        }
    }
}