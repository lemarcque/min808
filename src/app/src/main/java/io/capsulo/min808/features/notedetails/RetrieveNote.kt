package io.capsulo.min808.features.notedetails

import io.capsulo.min808.core.data.NoteRepository
import io.capsulo.min808.core.data.room.NoteEntity
import io.capsulo.min808.core.domain.ReactiveInteractor
import io.capsulo.min808.core.utils.CalendarUtils
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option
import kotlin.math.sin

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
                        it.id, it.title, it.content, it.author,
                        "on ${CalendarUtils.getHumanRedableDate(it.date)} · ${minToRead(it.content)} min read"
                    )
                }
        }

        return single
            ?: Single.error(Throwable("The parameter 'id' is required to perform a request"))
    }

    /**
     * Give an estimated time to read based on his content
     */
    private fun minToRead(content: String?): Int {
        content?.let {
            val wpm = 200
            val SPACE_CHAR = ' '
            val w = content.split(SPACE_CHAR)
            val t = w.size / wpm
            return if (w.size <= wpm) 1 else t
        }
        return 0
    }
}