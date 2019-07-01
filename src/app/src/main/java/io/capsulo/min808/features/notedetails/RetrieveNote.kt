package io.capsulo.min808.features.notedetails

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.capsulo.min808.core.utils.CalendarUtils
import io.reactivex.Single
import polanski.option.Option

/**
 * Get a no from his id
 */
class RetrieveNote(private  val repository: NoteRepository):
        ReactiveInteractor.RetrieveInteractor<Int, NoteDetailsView> {


    override fun getBehaviorStream(params: Option<Int>): Single<NoteDetailsView> {
        var single: Single<NoteDetailsView>? = null
        params.ifSome {
            single = repository
                .getNote(it)
                .map {
                    NoteDetailsView(
                        it.id,
                        it.title,
                        it.content,
                        it.author,
                        getFormattedDate(it.date, it.content),
                        it.bookmark
                    )
                }
        }

        return single
            ?: Single.error(Throwable("The parameter 'id' is required to perform a request"))
    }


    fun getFormattedDate(date: Long?, content: String?) =
        "on ${CalendarUtils.getHumanRedableDate(date)} · ${minToRead(content)} min read"

    /**
     * Give an estimated time to read based on his content
     */
    private fun minToRead(content: String?): Int {
        content?.let {
            val wpm = 200
            val SPACE_CHAR = ' '
            val w = content.split(SPACE_CHAR)
            val textDifficulty = 1
            val t = w.size / wpm * textDifficulty
            return if (w.size <= wpm) 1 else t
        }
        return 0
    }
}