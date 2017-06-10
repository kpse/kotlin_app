package com.example.qsuo.kotlinapp.features.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qsuo.kotlinapp.R
import com.example.qsuo.kotlinapp.commons.RedditNewsItem
import com.example.qsuo.kotlinapp.commons.adapter.NewsAdapter
import com.example.qsuo.kotlinapp.commons.adapter.ViewTypeDelegateAdapter
import com.example.qsuo.kotlinapp.commons.inflate
import kotlinx.android.synthetic.main.news_fragment.*

class NewsFragment: Fragment() {

    private val newsList by lazy {
        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(context)
        news_list
    }
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        return container?.inflate(R.layout.news_fragment)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

        if (savedInstanceState == null) {
            val news = mutableListOf<RedditNewsItem>()
            for (i in 1..10) {
                news.add(RedditNewsItem(
                        "author$i",
                        "Title $i",
                        i,
                        1457207701L - i * 2000,
                        "http://lorempixel.com/200/200/technics/$i",
                        "url"
                ))
            }
            (newsList.adapter as NewsAdapter).addNews(news)
        }
    }

    private fun initAdapter() {
        if(newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
    }
}

