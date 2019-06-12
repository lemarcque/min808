package io.capsulo.base.data.store

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Interface for any kind of store.
 *
 * sources : https://github.com/n26/N26AndroidSamples/blob/master/base/src/main/java/de/n26/n26androidsamples/base/data/store/ReactiveStore.java
 */
interface ReactiveStore<Key, Value> {


    // Get
    fun getSingular(model: Key) : Single<Value>

    fun getAll() : Single<List<Value>>

    // Put

    fun storeSingular(model: Value): Completable

    fun storeAll(modelList: List<Value>): Completable

    fun replaceAll(modelList: List<Value>)

    fun clear()

}