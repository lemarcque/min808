package io.capsulo.min808.core.presentation

import androidx.fragment.app.Fragment

/**
 * Base class for Fragment
 */
open class BaseFragment : Fragment() {

    /**
    * Could handle back press.
    * @return true if back press was handled
    */
    open fun onBackPressed(): Boolean = false


}