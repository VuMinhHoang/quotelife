package giavu.hoangvm.japanfood.activity.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import giavu.hoangvm.japanfood.R
import giavu.hoangvm.japanfood.api.LeaguesApi
import giavu.hoangvm.japanfood.core.retrofit.JFDApiAccessor
import giavu.hoangvm.japanfood.databinding.ActivityMainBinding
import giavu.hoangvm.japanfood.model.Category
import giavu.hoangvm.japanfood.usecase.CategoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val categoryUseCase: CategoryUseCase by inject()
    val compositeDisposable = CompositeDisposable()

    val viewModel : MainViewModel by lazy {
        ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDataBinding()
        Log.d("Print", "Call oncreate")
        //fetchCategoris()
        fetchLeagues()
    }

    private fun observerViewModel(){

    }

    private fun fetchLeagues(){
        JFDApiAccessor(this@MainActivity).from().using(LeaguesApi::class.java)
                .getLeaguesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Log.d("Test Retrofit", "Ngon day")
                        },
                        onError = {Log.d("Test Retrofit", it.toString())}
                )
    }

    private fun initializeDataBinding(){
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this@MainActivity, R.layout.activity_main)
                .apply {
                    viewModel = this@MainActivity.viewModel
                    setLifecycleOwner(this@MainActivity)
                }

    }
    private fun onSuccess(result: List<Category>) {
        Log.d("Print string:%s", result.toString())
    }

    private fun fetchCategoris(){
        categoryUseCase.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    onSuccess(it)
                })
                .addTo(compositeDisposable)
    }
    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }


}
