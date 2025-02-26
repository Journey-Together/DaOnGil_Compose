package kr.techit.lion.data.common

import kr.techit.lion.domain.exception.HttpError.AuthenticationError
import kr.techit.lion.domain.exception.HttpError.AuthorizationError
import kr.techit.lion.domain.exception.HttpError.BadRequestError
import kr.techit.lion.domain.exception.NetworkError.ConnectError
import kr.techit.lion.domain.exception.NetworkError
import kr.techit.lion.domain.exception.HttpError.NotFoundError
import kr.techit.lion.domain.exception.Result
import kr.techit.lion.domain.exception.HttpError.ServerError
import kr.techit.lion.domain.exception.NetworkError.TimeoutError
import kr.techit.lion.domain.exception.NetworkError.UnknownError
import kr.techit.lion.domain.exception.NetworkError.UnknownHostError
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 주어진 [block] 코드를 실행하고 발생할 수 있는 예외를 [NetworkError] 인스턴스로 변환하여 처리합니다.
 *
 * @param block 실행할 코드 블록.
 * @return [block] 실행 결과를 포함하는 [Result.Success] 또는 예외 발생 시 [NetworkError]를 포함하는 [Result.Error] 객체.
 */
inline fun <T> execute(block: () -> T): Result<T> = runCatching {
    Result.Success(block())
}.getOrElse {
    Result.Error(handleNetworkError(it))
}

/**
 * [Throwable]을 해당 유형에 따라 특정 [NetworkError]로 매핑합니다.
 *
 * @param e 매핑할 [Throwable].
 * @return 해당하는 [NetworkError].
 */
fun handleNetworkError(e: Throwable): NetworkError {
    return when (e) {
        is ConnectException -> ConnectError
        is SocketTimeoutException -> TimeoutError
        is UnknownHostException -> UnknownHostError
        is HttpException -> {
            when (e.code()) {
                400 -> BadRequestError
                401 -> AuthenticationError
                403 -> AuthorizationError
                404 -> NotFoundError
                else -> ServerError
            }
        }
        else -> UnknownError
    }
}