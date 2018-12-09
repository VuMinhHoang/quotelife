package giavu.hoangvm.japanfood.usecase

import giavu.hoangvm.japanfood.api.CategoryApi
import io.reactivex.Single
import query.CategoryQuery

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
class CategoryUseCase(private val categoryApi: CategoryApi) {

    fun getCategory(): Single<String> {
        return categoryApi.get().map { response ->
            response.data()?.let {
                convert(it)
            }
        }
    }

    private fun convert(response: CategoryQuery.Data) : String{
            return ""
    }
}