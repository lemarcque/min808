package io.capsulo.base.data.store

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Interface for any type of store. Don't implement this directly,
 * use [MemoryStore] or [DiskStore] so it is more
 * descriptive.
 *
 * source : https://github.com/n26/N26AndroidSamples/blob/master/base/src/main/java/de/n26/n26androidsamples/base/data/store/Store.java
 */
interface Store<Key, Value> {



    fun getSingular(key: Key) : Single<Value>
    fun getAll() : Single<List<Value>>
    fun putSingular(value: Value): Completable
    fun putAll(valueList: List<Value>): Completable
    fun clear()


    /**
     * More descriptive interface for memory based stores.
     */
    interface MemoryStore<Key, Value> : Store<Key, Value>

    /**
     * More descriptive interface for disk based stores.
     */
    interface DiskStore<Key, Value> : Store<Key, Value>
}