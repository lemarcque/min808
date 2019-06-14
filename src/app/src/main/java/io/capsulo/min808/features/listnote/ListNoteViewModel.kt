package io.capsulo.min808.features.listnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.capsulo.min808.core.utils.CalendarUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import polanski.option.Option
import polanski.option.Option.none


/**
 * Class responsible to provide data to the UI and survive configuration changes.
 * Acts as a communication center between the Repository and the UI.
 */
class ListNoteViewModel(val interactor: RetrieveNoteList) : ViewModel() {

    private var notesLiveData: MutableLiveData<List<NoteView>>? = null


    fun getNotesLiveData(): MutableLiveData<List<NoteView>> {
        if(notesLiveData == null) {
            notesLiveData = MutableLiveData()
            getNotes("")
        }
        return notesLiveData as MutableLiveData<List<NoteView>>
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



}