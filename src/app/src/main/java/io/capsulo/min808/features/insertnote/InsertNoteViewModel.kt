package io.capsulo.min808.features.insertnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import polanski.option.Option

/**
 * Class that provide data to the UI and survive configuration changes.
 * Acts as a communication center between the Repository and the UI.
 */
class InsertNoteViewModel(private val interactor: StoreNote) : ViewModel() {


    private var insertionMessageStatusLiveData: MutableLiveData<String>? = null
    private var insertionStatusLiveData: MutableLiveData<Boolean>? = null

    /**
     * Return the state
     * with a message of the authentication process.
     *
     * @return Notification Status of the authentication process wrapped in a Live Data
     */
    fun getInsertionMessageStatusLiveData(): MutableLiveData<String> {
        if (insertionMessageStatusLiveData == null)
            insertionMessageStatusLiveData = MutableLiveData()
        return insertionMessageStatusLiveData as MutableLiveData<String>
    }

    /**
     * Return a boolean indicating if the authentication was successful
     *
     * @return a boolean that indicates that authentication was successful.
     */
    fun getInsertionStatusLiveData() : MutableLiveData<Boolean> {
        if (insertionStatusLiveData == null)
            insertionStatusLiveData = MutableLiveData()
        return insertionStatusLiveData as MutableLiveData<Boolean>
    }

    /**
     * Store the input text from the user.
     */
    fun storeNote(rawText: String) {
        interactor
            .getSingle(Option.ofObj(rawText))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { insertionStatusLiveData?.postValue(true) },
                { insertionMessageStatusLiveData?.postValue(it.message) }
            )
    }

}