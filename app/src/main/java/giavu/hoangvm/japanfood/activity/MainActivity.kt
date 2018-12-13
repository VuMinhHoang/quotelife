package giavu.hoangvm.japanfood.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import giavu.hoangvm.japanfood.R
import giavu.hoangvm.japanfood.usecase.CategoryUseCase
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val categoryUseCase: CategoryUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = categoryUseCase.getCategory().toFuture().get(1, TimeUnit.SECONDS)
    }
}
