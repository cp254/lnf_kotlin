package io.ginius.cp.kt.lostfound.kotlin

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import butterknife.BindView
import io.ginius.cp.kt.lostfound.kotlin.api.ApiService
import io.reactivex.disposables.Disposable
import android.util.Log
import android.view.View
import android.widget.*
import io.ginius.cp.kt.lostfound.R
import io.ginius.cp.kt.lostfound.R.id.drawer_layout
import io.ginius.cp.kt.lostfound.kotlin.api.post.SearchDoc
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    var opened : Boolean = false
    var closed : Boolean = false

    private val ApiServe by lazy {
        ApiService.create()
    }

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
                R.id.nav_search_history -> toast("Copy clicked")
                R.id.nav_logout -> toast("Paste clicked")
                R.id.action_new ->{
                    // Multiline action
//                    toast("New clicked")
//                    drawer_layout.setBackgroundColor(Color.RED)
                }

            }
            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //Task HERE
                //submitData()
                return false
            }

        })


    }

    private fun Context.toast(message:String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }

}

