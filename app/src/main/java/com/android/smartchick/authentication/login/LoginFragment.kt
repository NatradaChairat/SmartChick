package com.android.smartchick.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.smartchick.R
import com.android.smartchick.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginContract.View{

    override lateinit var presenter : LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = LoginPresenter(this)
        initListener()
    }

    override fun onResultLoaded(result: Boolean) {
        when(result){
            true -> {
//                activity?.let{
//                    startActivity(ChickActivity.newIntent(context!!))
//                }

                fragmentManager!!.beginTransaction().replace(R.id.contentFrame, DashboardFragment.newInstance()).addToBackStack(null).commit()

            }
            false -> {
                Toast.makeText(context!!, "Incorrect!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onError(error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initListener(){
        btnLogin.setOnClickListener {
            if(etEmail.text.isNullOrBlank() && etPassword.text.isNullOrBlank()){
                presenter.loginByEmailPassword("test@gmail.com", "1234567890")
            }else {
                presenter.loginByEmailPassword(etEmail.text.toString(), etPassword.text.toString())
            }
        }
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}