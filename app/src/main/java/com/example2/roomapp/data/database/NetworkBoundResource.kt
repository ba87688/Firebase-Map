package com.example2.roomapp.data.database


import kotlinx.coroutines.flow.*

inline fun <ResultType,RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult:suspend  (RequestType) ->Unit,
    crossinline shouldFetch:(ResultType) ->Boolean = {true}
) = flow {
    val data = query().first()
    //decide if we need to fetch data from api...

    val flow =  if(shouldFetch(data)){
        //not sure abt this one
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        }catch (throwable:Throwable){
            query().map { Resource.Error(throwable,it) }

        }
    }else{
        query().map { Resource.Success(it) }

    }
    emitAll(flow)
}