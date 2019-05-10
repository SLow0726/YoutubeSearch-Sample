package com.example.youtubetest

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var searchResults = arrayListOf<SearchItem>()
    private lateinit var adapter: MyAdapter


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = MyAdapter(this, searchResults)
        recyclerView.adapter = adapter

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            applicationContext,
            LinearLayout.VERTICAL, false
        )

        btn_input.setOnClickListener {


            //        btn_input.setOnEditorActionListener(object : TextView.OnEditorActionListener {
//            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
//
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            searchOnYoutube(keywords = et_input.text.toString())

                    val imm = getSystemService(
                        Context
                            .INPUT_METHOD_SERVICE
                    ) as InputMethodManager

                    imm.hideSoftInputFromWindow(
                        currentFocus!!.windowToken
                        , InputMethodManager.RESULT_UNCHANGED_SHOWN
                    )//響應Button的onClick事件將鍵盤隱藏

//                    return false
        }
//                return true
    }

    //        })
//
    fun searchOnYoutube(keywords: String) {

        Thread {

            val yc = YoutubeConnector(this@MainActivity)
            searchResults.clear()
            searchResults.addAll(yc.search(keywords)!!)
            this@MainActivity.runOnUiThread {

                fillYoutubeVideos()
            }

        }.start()
    }


    fun fillYoutubeVideos() {
        adapter.notifyDataSetChanged()
    }
}




