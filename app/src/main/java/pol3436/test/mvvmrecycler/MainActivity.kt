package pol3436.test.mvvmrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.fragment.app.findFragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pol3436.test.mvvmrecycler.databinding.ActivityMainBinding
import pol3436.test.mvvmrecycler.databinding.FragmentAddBinding
import pol3436.test.mvvmrecycler.fragments.add.AddFragment

class MainActivity : AppCompatActivity() {

    var _binding : ActivityMainBinding? = null
    private val  binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupActionBarWithNavController(findNavController(R.id.fragment))//actionbar
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding?.fragment?.let { findNavController(it) }

        return navController!!.navigateUp()||super.onSupportNavigateUp()
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}