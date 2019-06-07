package io.capsulo.min808.features.listnote

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.capsulo.min808.R
import io.capsulo.min808.core.navigation.Navigator
import kotlinx.android.synthetic.main.listnote_fragment.*

/**
 *  Display a list all notes stored.
 */
class ListNoteFragment : Fragment() {

    // Variable
    val TAG: String = ListNoteFragment::class.java.simpleName

    companion object {
        fun newInstance() = ListNoteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.listnote_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInterface()
    }

    private fun setInterface() {

        // configuration of app bar
        //toolbar_listnote.inflateMenu(R.menu.appbar_listnote)

        // handle click on fab
        fab_listnote.setOnClickListener { Navigator.showInsertNote(context!!) }

        // config recycler view
        // TODO : Mock
        val list = listOf(
            Note("Computer Science", "L'informatique est un domaine d'activité scientifique, technique et industriel concernant le traitement automatique de l'information par l'exécution de programmes informatiques par des machines", "10.06.2019"),
            Note("L'art", "L’art est une activité, le produit de cette activité ou l'idée que l'on s'en fait s'adressant délibérément aux sens, aux émotions, aux intuitions et à l'intellect. On peut affirmer que l'art est le propre de l'humain ou de toute autre conscience, ", "12.06.2019"),
            Note("Leonardo", "Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam eaque ipsa, quae ab illo inventore.", "13.06.2019"),
            Note("Leonardo", "veritatis et quasi architecto beatae vitae dicta sunt, explicabo. Nemo enim ipsam voluptatem, quia voluptas sit, aspernatur aut odit aut fugit, sed quia consequuntur ", "13.06.2019"),
            Note("Leonardo", "magni dolores eos, qui ratione voluptatem sequi nesciunt, neque porro quisquam est, qui dolorem ipsum, quia dolor sit, amet, consectetur, adipisci velit, sed quia", "14.06.2019"),
            Note("Leonardo", "non numquam eius modi tempora incidunt, ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem", "13.06.2019"),
            Note("Leonardo", "ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit, qui in ea voluptate velit esse, quam ", "13.06.2019"),
            Note("Leonardo", "blanditiis praesentium voluptatum deleniti atque corrupti, quos dolores et quas molestias excepturi sint, obcaecati cupiditate non provident, similique sunt in ", "13.06.2019"),
            Note("Leonardo", "culpa, qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta ", "13.06.2019")
        )

        recyclerview_listnote.apply {
            adapter = ListNoteAdapter(list)
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun showMessage(message: String) {
        val view = activity!!.findViewById<View>(android.R.id.content)
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

}