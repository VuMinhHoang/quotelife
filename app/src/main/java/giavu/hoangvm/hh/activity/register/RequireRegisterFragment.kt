package giavu.hoangvm.hh.activity.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import giavu.hoangvm.hh.R
import giavu.hoangvm.hh.databinding.FragmentRequireRegisterBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RequireRegisterFragment : Fragment() {

    private val sharedViewModel: RegisterAccountViewModel by sharedViewModel()
    private val viewModel: RequireRegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        initializeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            isValidate.observe(this@RequireRegisterFragment,
                Observer {
                    Timber.d("onChanged:%s", it.toString())
                    sharedViewModel.registerButtonEnabled.postValue(it)
                })
        }
    }

    private fun initializeViewModel() {
        viewModel.initialize(
            owner = this@RequireRegisterFragment
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentRequireRegisterBinding>(
            inflater,
            R.layout.fragment_require_register,
            container,
            false
        ).apply {
            setLifecycleOwner(this@RequireRegisterFragment)
            viewModel = this@RequireRegisterFragment.viewModel
        }.root
    }




    companion object {
        fun newInstance(): Fragment = RequireRegisterFragment()
    }
}
