package com.example.qsuo.kotlinapp.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qsuo.kotlinapp.R
import com.example.qsuo.kotlinapp.commons.RxBaseFragment
import com.example.qsuo.kotlinapp.commons.adapter.NewsAdapter
import com.example.qsuo.kotlinapp.commons.inflate
import kotlinx.android.synthetic.main.news_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewsFragment: RxBaseFragment() {

    private val newsList by lazy {
        news_list.setHasFixedSize(true)
        news_list.layoutManager = LinearLayoutManager(context)
        news_list
    }

    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

        if (savedInstanceState == null) {
            requestNews()
        }
    }

    private fun requestNews() {
        val subscription = newsManager.getNews()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
              retrieveNews ->
              (newsList.adapter as NewsAdapter).addNews(retrieveNews)
          }, {
              e ->
              Snackbar.make(newsList, e.message ?: "", Snackbar.LENGTH_LONG).show()
          })
        subscriptions.add(subscription)

    }

    private fun initAdapter() {
        if(newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
    }
}

