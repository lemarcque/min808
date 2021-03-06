package io.capsulo.min808.features.insertnote

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import io.capsulo.min808.R
import kotlinx.android.synthetic.main.fragment_insertnote.*


/**
 * Allow to insert a new note in database.
 */
class InsertNoteFragment(private val viewModel: InsertNoteViewModel) : Fragment() {

    companion object {
        // Factory method
        fun newInstance(viewModel: InsertNoteViewModel) = InsertNoteFragment(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getInsertionStatusLiveData().observe(this, Observer {
            activity!!.setResult(Activity.RESULT_OK)
            activity!!.finish()
        })
        viewModel.getInsertionMessageStatusLiveData().observe(this, Observer {
            val view = activity!!.findViewById<View>(android.R.id.content)
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insertnote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInterface()
    }

    private fun setInterface() {
        // configuration of app bar
        toolbar_insertnote.inflateMenu(R.menu.appbar_insertnote)
        toolbar_insertnote.setNavigationIcon(R.drawable.ic_close_black_24dp)
        toolbar_insertnote.setNavigationOnClickListener { activity?.finish() }
        toolbar_insertnote.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_publish_insertnote -> { viewModel.storeNote(edittext_note_insertnote.text.toString());true }
                else -> super.onOptionsItemSelected(it)
            }
        }

        // listener for edittext changing
        edittext_note_insertnote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val publishAction = toolbar_insertnote.menu.findItem(R.id.action_publish_insertnote)
                publishAction.isEnabled = s!!.isNotEmpty()
                val v = view?.findViewById<TextView>(R.id.action_publish_insertnote);
                if(publishAction.isEnabled) (v as TextView).setTextColor(Color.BLACK)
                else (v as TextView).setTextColor(ContextCompat.getColor(context!!, R.color.colorDisableGray))

            }
        })

        // set default color of menu item
        val v = view?.findViewById<TextView>(R.id.action_publish_insertnote);
        (v as TextView).setTextColor(ContextCompat.getColor(context!!, R.color.colorDisableGray))
    }

}
