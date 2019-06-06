package io.capsulo.min808.core.data

import io.capsulo.base.data.store.Store
import io.reactivex.Maybe

/**
 * Todo : Add class description
 */
class DatabaseStore : Store<String, String> {
    override fun getSingular(key: String): Maybe<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Maybe<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putAl(valueList: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putSingular(value: String) {

    }

}