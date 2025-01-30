package kr.techit.lion.network.service

import kr.techit.lion.network.response.sign.SignInResponse
import kr.techit.lion.network.service.authentication.AuthType
import kr.techit.lion.network.response.sign.SignUpResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Tag

interface AuthService {
    @POST("auth/sign-in")
    suspend fun signIn(
        @Query("type") type: String,
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody,
        @Tag authType: AuthType = AuthType.NO_AUTH
    ): SignInResponse

    @POST("auth/sign-out")
    suspend fun signOut(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    ): SignInResponse

    @GET("auth/reissue")
    suspend fun refresh(
        @Header("Authorization") refreshToken: String,
    ): SignUpResponse

    @DELETE("auth/withdrawal")
    suspend fun withdraw(
        @Tag authType: AuthType = AuthType.ACCESS_TOKEN
    )
}