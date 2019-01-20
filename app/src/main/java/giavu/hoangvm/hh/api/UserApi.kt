package giavu.hoangvm.hh.api

import giavu.hoangvm.hh.exeption.ResponseError
import giavu.hoangvm.hh.model.LoginResponse
import giavu.hoangvm.hh.model.RegUser
import giavu.hoangvm.hh.model.RegisterResponse
import giavu.hoangvm.hh.model.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author: Hoang Vu
 * @Date:   2019/01/04
 */
interface UserApi {
    @POST("session")
    fun login(@Body body: User): Single<LoginResponse>

    @POST("session")
    fun loginError(@Body body: User): Single<ResponseError>

    @POST("users")
    fun register(@Body body: RegUser): Single<RegisterResponse>
}