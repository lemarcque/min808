package io.capsulo.min808.features.listnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.capsulo.min808.core.utils.CalendarUtils
import io.capsulo.min808.features.notedetails.DeleteNote
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import polanski.option.Option
import polanski.option.Option.none


/**
 * Class responsible to provide data to the UI and survive configuration changes.
 * Acts as a communication center between the Repository and the UI.
 */
class ListNoteViewModel(private val interactor: RetrieveNoteList,
                        private val deleteNote: DeleteNote) : ViewModel() {

    var notesLiveData: MutableLiveData<List<NoteView>>? = null
        get() {
            if(field == null) {
                field = MutableLiveData()
                getNotes("")
            }
            return field as MutableLiveData<List<NoteView>>
        }
    var notesDeletedLiveData: MutableLiveData<Boolean>? = null
        get() {
            if(field == null) {
            field = MutableLiveData()
            getNotes("") }
            return field as MutableLiveData<Boolean>
        }

    fun getNotes(filter: String) {
        interactor
            .getBehaviorStream(Option.ofObj(filter))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap { Observable.fromIterable(it) }
            .map { NoteView(it.id, it.title, it.content, CalendarUtils.getHumanRedableDate(it.date)) }
            .toList()
            .doOnSuccess { notesLiveData?.postValue(it) }
            .doOnError { println(it.message) }
            .subscribe()
    }


    fun deleteNote(id: Int) = deleteNote
        .getSingle(Option.ofObj(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ println("deleted correctly."); notesDeletedLiveData?.postValue(true)}, { println("error while deleting.")})

}