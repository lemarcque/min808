package io.capsulo.min808.features.notedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class NoteDetailsViewModel(
    private val retrieveNote: RetrieveNote,
    private val updateNote: UpdateNote,
    private val deleteNote: DeleteNote) : ViewModel() {

    var noteLiveData: MutableLiveData<NoteDetailsView>? = null
        get() {
            if(field == null) field = MutableLiveData()
            return field as MutableLiveData<NoteDetailsView>
        }

    var noteUpdatedLiveDeta: MutableLiveData<Boolean>? = null
        get() {
            if(field == null) field = MutableLiveData()
            return field as MutableLiveData<Boolean>
        }

    var noteDeletedLiveDeta: MutableLiveData<Boolean>? = null
        get() {
            if(field == null) field = MutableLiveData()
            return field as MutableLiveData<Boolean>
        }


    fun getNote(id: Int) = retrieveNote
        .getBehaviorStream(Option.ofObj(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { noteLiveData?.postValue(it) },
            { println("${NoteDetailsViewModel::class.java} A problem occur when trying to retrieve Note !")   }
        )

    fun updateNote(note: NoteDetailsView) = updateNote
        .getSingle(Option.ofObj(note))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { noteUpdatedLiveDeta?.postValue(true) },
            { println("${NoteDetailsViewModel::class.java} A problem occur when trying to update Note !"); noteUpdatedLiveDeta?.postValue(false)   }
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
