package giavu.hoangvm.japanfood.api

import giavu.hoangvm.japanfood.model.LoginResponse
import giavu.hoangvm.japanfood.model.User
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