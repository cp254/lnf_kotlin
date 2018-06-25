package io.ginius.cp.kt.lostfound.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.ginius.cp.kt.lostfound.R
import io.ginius.cp.kt.lostfound.kotlin.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Register : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val ApiServe by lazy {
        ApiService.create()
    }

//    @BindView(R.id.toolbar)
//    lateinit var mToolbar: Toolbar

    @BindView(R.id.et_contact)
    lateinit var etContact: EditText
    @BindView(R.id.et_email)
    lateinit var etEmail: EditText
    @BindView(R.id.et_password)
    lateinit var etPassword: EditText
    @BindView(R.id.btn_submit)
    lateinit var bSubmit: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        ButterKnife.bind(this)
//        bSubmit.setOnClickListener {
//            submitData()
//        }
    }

    @OnClick
    (R.id.btn_submit)
    fun submitData() {
        val registerRequest: io.ginius.cp.kt.lostfound.kotlin.api.post.Register.Request
        val registerData: io.ginius.cp.kt.lostfound.kotlin.api.post.Register.Data = io.ginius.cp.kt.lostfound.kotlin.api.post.Register.Data(etPassword.toString(), "self",
                etEmail.toString(), etContact.toString())
        registerRequest = io.ginius.cp.kt.lostfound.kotlin.api.post.Register.Request("register_user", registerData)
        Toast.makeText(this, "Loading ..", Toast.LENGTH_SHORT).show()
        disposable = ApiServe.reg(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e("First call", "huraay: " + result.statusname)

                            Toast.makeText(this, result.statusname, Toast.LENGTH_LONG).show()
                        },
                        { error ->
                            Log.i("aldieemaulana", "aldieemaulana: " + error.localizedMessage)
                            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                        }
                )
    }



    // abstract inner class Car internal constructor(private val name: String)


}