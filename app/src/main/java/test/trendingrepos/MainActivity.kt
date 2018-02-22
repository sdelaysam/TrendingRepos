package test.trendingrepos

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import test.trendingrepos.databinding.ActivityMainBinding
import test.trendingrepos.repos.ReposFragment

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        var fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == null) {
            fragment = ReposFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }
    }

}