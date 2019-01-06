package giavu.hoangvm.hh.usecase

import android.util.Log
import giavu.hoangvm.hh.api.CategoryApi
import giavu.hoangvm.hh.model.Category
import io.reactivex.Single
import query.CategoryQuery

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/09
 */
class CategoryUseCase(private val categoryApi: CategoryApi) {

    fun getCategory(): Single<List<Category>> {
        return categoryApi.get().map { response ->
            response.data()?.let {
                convert(it)
            }
        }
    }

    private fun convert(response: CategoryQuery.Data): List<Category> {
        Log.d("Print", "Call convert")
        return response.categories()?.category()?.mapNotNull { category ->
            category.title()?.let {
                makeCategory(it)
            }
        } ?: emptyList()
    }

    private fun makeCategory(title: String): Category {
        return Category(title)
    }
}