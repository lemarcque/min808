package io.capsulo.base.data.store

import io.reactivex.Observable


/**
 * Interface for any kind of store.
 *
 * sources : https://github.com/n26/N26AndroidSamples/blob/master/base/src/main/java/de/n26/n26androidsamples/base/data/store/ReactiveStore.java
 */
interface ReactiveStore<Key, Value> {


    // Get
    fun getSingular(model: Key) : Observable<Value>

    fun getAll() : Observable<List<Value>>

    // Put

    fun storeSingular(model: Value)

    fun storeAll(modelList: List<Value>)

    fun replaceAll(modelList: List<Value>)

}