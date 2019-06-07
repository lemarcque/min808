package io.capsulo.min808.core.domain

import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option

/**
 * Interfaces for Interactors.
 * This interfaces represent use cases (this means any use case in the application should implement
 * this contract).
 */

interface ReactiveInteractor {


    /**
     * Retrieves changes from the data layer.
     * It returns an [Flowable] that emits updates for the retrieved object. The returned [Flowable] will never complete,
     * but it can error if there are any problems performing the required actions to serve the data.
     */
    interface RetrieveInteractor<Param, Object> :
        ReactiveInteractor {

        fun getBehaviorStream(params: Param) : Observable<Object>
    }

    /**
     * The request interactor is used to request some result once. The returned observable is a single, emits once and then completes or errors.
     * @param <Params> the type of the returned data.
     * @param <Result> required parameters for the request.
    </Result></Params>
     */
    interface RequestInteractor<Params, Result> :
        ReactiveInteractor {

        fun getSingle(params: Params): Single<Result>
    }

    interface SendInteractor<Params, Result> {

        fun getSingle(params: Option<Params>): Single<Result>
    }

}