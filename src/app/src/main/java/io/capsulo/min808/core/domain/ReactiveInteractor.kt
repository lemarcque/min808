package io.capsulo.min808.core.domain

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import polanski.option.Option

/**
 * Interfaces for Interactors.
 * This interfaces represent use cases (this means any use case in the application should implement
 * this contract).
 *
 * sources : https://github.com/n26/N26AndroidSamples/blob/master/base/src/main/java/de/n26/n26androidsamples/base/domain/ReactiveInteractor.java
 */

interface ReactiveInteractor {


    /**
     * Retrieves changes from the data layer.
     * It returns an [Flowable] that emits updates for the retrieved object. The returned [Flowable] will never complete,
     * but it can error if there are any problems performing the required actions to serve the data.
     */
    interface RetrieveInteractor<Param, Object> :
        ReactiveInteractor {

        fun getBehaviorStream(params: Option<Param>) : Single<Object>
    }

    /**
     * The request retrieveNote is used to request some result once. The returned observable is a single, emits once and then completes or errors.
     * @param <Params> the type of the returned data.
     * @param <Result> required parameters for the request.
    </Result></Params>
     */
    interface RequestInteractor<Params, Result> :
        ReactiveInteractor {

        fun getSingle(params: Option<Params>): Single<Result>
    }


    /**
     * Sends changes to data layer.
     * It returns a [Single] that will emit the result of the send operation.
     * @param <Result> the type of the send operation result.
     * @param <Params> required parameters for the send.
    </Params></Result> */
    interface SendInteractor<Params, Result> {

        fun getSingle(params: Option<Params>): Completable
    }

    /**
     * The delete retrieveNote is used to delete entities from data layer. The response for the delete operation comes as onNext
     * event in the returned observable.
     * @param <Result> the type of the delete response.
     * @param <Params>   required parameters for the delete.
    </Params></Result> */
    interface DeleteInteractor<Params, Result> : ReactiveInteractor {

        fun getSingle(params: Option<Params>): Single<Result>
    }

}