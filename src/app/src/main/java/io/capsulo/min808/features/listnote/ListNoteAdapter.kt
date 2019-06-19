package io.capsulo.min808.features.listnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.capsulo.min808.R
import java.util.*

/**
 * Populate the data into RecyclerVoew
 */
class ListNoteAdapter(val listener: OnItemClickListener) : RecyclerView.Adapter<ListNoteAdapter.ViewHolder>() {

    private var listNote: MutableList<NoteView> = mutableListOf()
    private var recentlyDeletedItemPosition: Int = -1
    private var recentlyDeletedItem: NoteView? = null

    constructor(notes: MutableList<NoteView>, listener: OnItemClickListener): this(listener) {
        listNote = notes
    }

    /**
     * Stores multiple Views inside the tag field of the Layout
     * so they can be immediately loaded.
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var id: Int = -1

        fun setData(note: NoteView) {
            id = note.id ?: -1
            itemView.findViewById<TextView>(R.id.title_listnote_item).text = note.title
            itemView.findViewById<TextView>(R.id.content_listnote_item).text = note.content
            itemView.findViewById<TextView>(R.id.date_listnote_item).text = note.date
        }

        fun setEvent() {
            itemView.setOnClickListener { this@ListNoteAdapter.listener.onClick(id) }
        }
    }

    interface OnItemClickListener {
        fun onClick(id: Int)
        fun onItemDelete(id: Int)
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.listnote_recyclerview_item, parent, false)
        return ViewHolder(layout)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = listNote.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(listNote[position])
        holder.setEvent()
    }

    fun onItemDismiss(position: Int) {
        recentlyDeletedItem = listNote.removeAt(position)
        notifyItemRemoved(position)
        recentlyDeletedItemPosition = position
        listener.onItemDelete(recentlyDeletedItem?.id!!)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listNote, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listNote, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    fun update(notes: MutableList<NoteView>) {
        listNote = notes
        notifyDataSetChanged()
    }

    fun update(id: Int) {
        if(recentlyDeletedItem != null) {
            listNote.add(recentlyDeletedItemPosition, recentlyDeletedItem!!)
            recentlyDeletedItem = null
            notifyItemInserted(recentlyDeletedItemPosition)
        }
    }

}