package com.example.youtubetest

import android.content.Context
import android.util.Log
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.model.SearchResult
import java.io.IOException
import com.google.api.services.youtube.YouTube




class YoutubeConnector(context: Context) {
    private var youtube: YouTube? = null
    private var query: YouTube.Search.List? = null
    private val packageName = context.packageName

    init {

        youtube = YouTube.Builder(NetHttpTransport(), JacksonFactory(),
            HttpRequestInitializer { request ->

                request.headers.set("X-Android-Package", packageName)
                request.headers.set("X-Android-Cert", SHA1)

            }).setApplicationName("YoutubeTest").build()



        try {
            query = youtube!!.search().list("id,snippet")
            query!!.key = KEY
            query!!.type = "video"
            query!!.fields = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)"

        } catch (e: IOException) {
            Log.d("YC", "Could not initialize: $e")
        }

    }

    fun search(keywords: String): ArrayList<SearchItem>? {
        query!!.q = keywords
        query!!.maxResults = MAXRESULTS

        return try {
            val response = query!!.execute()
            val results = response.items
            var items = arrayListOf<SearchItem>()
            if (results != null) {
                items = setItemsList(results.iterator())
            }
            items
        } catch (e: IOException) {
            Log.d("YC", "Could not search: $e")
            null
        }
    }

    companion object {

        val KEY = "AIzaSyA2t6xT2yx8HsrrwfSPREc_mR_doJt8ung"
        val SHA1 = "E9:62:17:00:E5:52:8F:E7:D4:31:A2:B1:E6:1D:BC:68:C4:08:D3:A9"
        val MAXRESULTS: Long = 25

        private fun setItemsList(iteratorSearchResults: Iterator<SearchResult>): ArrayList<SearchItem> {
            val tempSetItems = arrayListOf<SearchItem>()
            if (!iteratorSearchResults.hasNext()) {
                System.out.println(" There aren't any results for your query.")
            }
            while (iteratorSearchResults.hasNext()) {
                val singleVideo = iteratorSearchResults.next()
                val rId = singleVideo.id
                if (rId.kind == "youtube#video") {
                    val item = SearchItem()
                    val thumbnail = singleVideo.snippet.thumbnails.high

                    item.id = singleVideo.id.videoId
                    item.title = singleVideo.snippet.title
                    item.description = singleVideo.snippet.description
                    item.thumbnailURL = thumbnail.url

                    tempSetItems.add(item)

                    println(" Video Id" + rId.videoId)
                    println(" Title: " + singleVideo.snippet.title)
                    println(" Thumbnail: " + thumbnail.url)
                    println(" Description: " + singleVideo.snippet.description)
                    println("\n-------------------------------------------------------------\n")
                }
            }
            return tempSetItems
        }
    }
}

