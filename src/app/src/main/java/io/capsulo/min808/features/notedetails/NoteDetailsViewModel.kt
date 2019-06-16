package io.capsulo.min808.features.notedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class NoteDetailsViewModel(
    val retrieveNote: RetrieveNote,
    val deleteNote: DeleteNote) : ViewModel() {

    private var noteLiveData: MutableLiveData<NoteDetailsView>? = null
    private var noteDeletedLiveDeta: MutableLiveData<Boolean>? = null


    // TODO : Use Getter
    fun getNoteLiveData(): MutableLiveData<NoteDetailsView> {
        if(noteLiveData == null) {
            noteLiveData = MutableLiveData()
        }
        return noteLiveData as MutableLiveData<NoteDetailsView>
    }


    // TODO: Use Getter
    fun getNoteDeletedLiveData(): MutableLiveData<Boolean> {
        if(noteDeletedLiveDeta == null) {
            noteDeletedLiveDeta = MutableLiveData()
        }
        return noteDeletedLiveDeta as MutableLiveData<Boolean>
    }

    fun getNote(id: Int) = retrieveNote
        .getBehaviorStream(Option.ofObj(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { noteLiveData?.postValue(it) },
            { println("${NoteDetailsViewModel::class.java} A problem occur when trying to retrieve Note !")   }
        )


    fun deleteNote(id: Int) = deleteNote
        .getSingle(Option.ofObj(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { noteDeletedLiveDeta?.postValue(true) },
            { println(it.message) }
        )

}
