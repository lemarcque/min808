package io.capsulo.min808.presentation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.capsulo.min808.R
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Display the logo
 */
class HomeFragment : Fragment() {

    // Variable
    val TAG: String = HomeFragment::class.java.simpleName

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "View displayed")
        init()
    }

    fun init() {
        var counter = 0
        val interval = 9L // 1/10 Second
        val MAX_TIME = 36 // 1 second
        val MILESTONE = MAX_TIME / 4 // 250
        val handler = Handler()

        // Bitmap
        var index = 0
        val img = arrayOf(
            R.drawable.logo_4,
            R.drawable.logo_3,
            R.drawable.logo_2,
            R.drawable.logo_1
        )

        Handler().postDelayed(object : Runnable {
            override fun run() {
                if(counter < MAX_TIME) {
                    if(counter % MILESTONE == 0) {
                        imageview_logo_home.background = activity!!.getDrawable(img[index])
                        index++
                    }

                    handler.postDelayed(this, interval)
                    counter++
                }
            }
        }, interval)
    }

}