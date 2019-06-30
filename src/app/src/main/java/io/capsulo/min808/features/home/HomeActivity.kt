package io.capsulo.min808.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.capsulo.min808.R


/**
 * Display the logo
 */
class HomeActivity : AppCompatActivity() {

    // Variable
    val TAG: String? = HomeActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_base)
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.base_activity, HomeFragment.newInstance())
            .commit()
    }

}