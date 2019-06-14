package io.capsulo.min808.features.notedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.capsulo.min808.core.utils.CalendarUtils
import io.capsulo.min808.features.listnote.NoteView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

class NoteDetailsViewModel(val interactor: RetrieveNote) : ViewModel() {

    private var noteLiveData: MutableLiveData<NoteDetailsView>? = null


    fun getNoteLiveData(): MutableLiveData<NoteDetailsView> {
        if(noteLiveData == null) {
            noteLiveData = MutableLiveData()
        }
        return noteLiveData as MutableLiveData<NoteDetailsView>
    }

    fun getNote(id: Int) {
        interactor
            .getBehaviorStream(Option.ofObj(id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { noteLiveData?.postValue(it)  },
                Consumer { println("${NoteDetailsViewModel::class.java} A problem occur when trying to retrieve Note !")   }
            )
    }

}
