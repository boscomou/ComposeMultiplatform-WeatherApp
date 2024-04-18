package network

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> toResultFlow(call: suspend () -> NetWorkResult<T?>) : Flow<NetWorkResult<T>> {
    return flow {
        emit(NetWorkResult.Loading(true))
        val c = call.invoke()
        c.let { response ->
            try {
                Logger.i("zxcv${response.data}")
                emit(NetWorkResult.Success(response.data))
            } catch (e: Exception) {
                 Logger.i("zxcv",e)
            }
        }
    }
}
