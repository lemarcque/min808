package io.capsulo.min808.features.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.capsulo.min808.R
import io.capsulo.min808.core.navigation.Navigator

/**
 * Display the logo
 */
class HomeFragment : Fragment() {

    // Variable
    val TAG: String = HomeFragment::class.java.simpleName

    companion object {
        // Factory method
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInterface()
    }

    /**
     * Configuration of the user interface elements.
     */
    private fun setInterface() {
        var counter = 0
        val interval = 500L
        val handler = Handler()

        // Start a timer
        handler.postDelayed(object : Runnable {
            val MAX_COUNT = 3
            override fun run() {
                if(counter < MAX_COUNT) {
                    handler.postDelayed(this, interval)
                    counter++
                }else {
                    Navigator.showListNote(context!!)
                }
            }
        }, interval)
    }

}