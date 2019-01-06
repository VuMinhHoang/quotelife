package giavu.hoangvm.hh.api

import giavu.hoangvm.hh.model.LoginResponse
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
}