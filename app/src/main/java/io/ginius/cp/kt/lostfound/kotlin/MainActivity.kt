package io.ginius.cp.kt.lostfound.kotlin

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import io.ginius.cp.kt.lostfound.R
import io.ginius.cp.kt.lostfound.kotlin.PreferenceHelper.defaultPrefs
import io.ginius.cp.kt.lostfound.kotlin.PreferenceHelper.get
import io.ginius.cp.kt.lostfound.kotlin.PreferenceHelper.set
import io.ginius.cp.kt.lostfound.kotlin.SearchDocumentRequest.Data
import io.ginius.cp.kt.lostfound.kotlin.adapter.DocAdapter
import io.ginius.cp.kt.lostfound.kotlin.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.success_dialog.*


class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    var opened : Boolean = false
    var closed : Boolean = false
    var MSG: String = ""
    lateinit var docNo: String
    private val ApiServe by lazy {
        ApiService.create()
    }
    val prefs = defaultPrefs(this)
    private var positiveDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the action bar drawer toggle instance
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            override fun onDrawerClosed(view:View){
                super.onDrawerClosed(view)
                //toast("Drawer closed")
                opened = false
                closed = true
            }

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
                opened = true
                closed = false
            }
        }


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = false
        //drawerToggle.drawerArrowDrawable
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        btn_search.setOnClickListener {
            searchDoc(docNo)
        }

        toolbar_menu.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }


        // Set navigation view navigation item selected listener
        nav_view.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.nav_profile -> toast("Cut clicked")
                R.id.nav_search_history -> {
                    searchHistory("8")
                }
                R.id.nav_logout -> toast("Paste clicked")
                R.id.action_new ->{
                    // Multiline action
//
                }

            }
            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                docNo = newText
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchDoc(query)
                return false
            }

        })

        rv.layoutManager = LinearLayoutManager(this)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
        rv.layoutManager = GridLayoutManager(this, 1)

        // Access the RecyclerView Adapter and load the data into it


    }

    fun searchDoc(docNumber: String) {
        MSG = "till noon"
        showDialog()
        val searchRequest: io.ginius.cp.kt.lostfound.kotlin.SearchDocumentRequest
        val searchRequestData: SearchDocumentRequest.Data = Data(docNumber, 8)
        searchRequest = io.ginius.cp.kt.lostfound.kotlin.SearchDocumentRequest("view_document_by_unique_ref", searchRequestData)
        disposable = ApiServe.searchDoc(searchRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            rv.visibility = View.VISIBLE
                            rv.adapter = DocAdapter(result.result.doc, this)
                            prefs["JUG"] = "any_type_of_value" //setter
                            val value: String? = prefs[Utils.USER_ID, ""] //getter
                            val anotherValue: Int? = prefs[Utils.USER_EMAIL, 10] //getter with default value

                            prefs[Utils.USER_EMAIL] = "name"

                            //get any value from prefs
                            val name: String? = prefs[Utils.USER_EMAIL]

                        },
                        { error ->
                            dialogErrorConfig(error.message)
                        }
                )
    }

    fun searchHistory(userID: String) {
        val searchHistoryRequest: io.ginius.cp.kt.lostfound.kotlin.SearchHistoryRequest
        val searchHistoryRequestData: SearchHistoryRequest.Data = SearchHistoryRequest.Data(userID)
        searchHistoryRequest = io.ginius.cp.kt.lostfound.kotlin.SearchHistoryRequest("get_user_search_history", searchHistoryRequestData)
        disposable = ApiServe.searchHistory(searchHistoryRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            val dialog = Dialog(this)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setContentView(R.layout.dialog_search_history)
                            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.setCanceledOnTouchOutside(false)
                            val docHistory: List<SearchHistoryResponse.Result> = result.result
                            val recView: RecyclerView
                            val btnClose: Button
                            recView = dialog.findViewById(R.id.rc)
                            btnClose = dialog.findViewById(R.id.btn_close)
                            recView.layoutManager = LinearLayoutManager(this)
                            recView.layoutManager = GridLayoutManager(this, 1)
                            recView.adapter = DocSearchAdapter(docHistory, this)
                            btnClose.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.show()
                        },
                        { error ->
                            dialogErrorConfig(error.message)

                        }
                )
    }

    fun dialogConfig(msg: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.success_dialog)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        if (msg != null)
            msg_text.text = msg
        btn_next.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun dialogErrorConfig(msg: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        if (msg != null)
            msg_text.text = msg
        btn_next.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    inline fun Activity.showNotesAlertDialog(func: PositiveDialog.() -> Unit): AlertDialog =
            PositiveDialog(this, MSG).apply {
                func()
            }.create()


    private fun showDialog() {

        positiveDialog = showNotesAlertDialog {}
    }

    private fun Context.toast(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }

}

