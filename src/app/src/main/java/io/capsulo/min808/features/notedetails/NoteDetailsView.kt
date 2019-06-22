package io.capsulo.min808.features.notedetails

/**
 * Class for display data in View.
 */
data class NoteDetailsView(
    //val position: Int,
    val id: Int?,
    val title: String?,
    val content: String?,
    val author: String?,
    val date: String,
    val bookmarked: Boolean?
)