package giavu.hoangvm.japanfood.core.retrofit

import okhttp3.Headers
import okhttp3.Request


/**
 * @Author: Hoang Vu
 * @Date:   2019/01/03
 */
class RequestFactory(private val headers: Map<String, String>, private val request: Request) {


    fun create(): Request {
        val builder = request.newBuilder()
        builder.headers(Headers.of(headers))

        /*
           実装メモ
            - Retrofit 自体にはエンドポイントごとに認証の有無を切り替えるような仕組みはない
            - ただしエンドポイントごとにヘッダを指定できる仕組み @Headers(...) はある
            - そのため、一旦メソッド定義時に @Brooklyn: NoAuth という独自のヘッダを指定して、
              ここで @Brooklyn ヘッダを全て削除するという方法をとっている

           補足メモ
            - 認証以外でエンドポイントごとに何らかの処理を切り替える必要が生じた場合も
              同様に @Brooklyn ヘッダに他の値を用意することで対応できる

           rf. http://stackoverflow.com/a/37823425
         */

        return builder.build()
    }

}
