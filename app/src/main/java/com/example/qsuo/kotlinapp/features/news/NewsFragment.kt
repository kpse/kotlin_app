package com.example.qsuo.kotlinapp.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qsuo.kotlinapp.R
import com.example.qsuo.kotlinapp.commons.InfiniteScrollListener
import com.example.qsuo.kotlinapp.commons.RedditNews
import com.example.qsuo.kotlinapp.commons.RxBaseFragment
import com.example.qsuo.kotlinapp.commons.adapter.NewsAdapter
import com.example.qsuo.kotlinapp.commons.inflate
import kotlinx.android.synthetic.main.news_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class NewsFragment : RxBaseFragment() {

  companion object {
    private val KEY_REDDIT_NEWS = "redditNews"
  }

  private var redditNews: RedditNews? = null
  private val newsList by lazy {
    news_list.setHasFixedSize(true)
    val linearLayout = LinearLayoutManager(context)
    news_list.layoutManager = linearLayout
    news_list.clearOnScrollListeners()
    news_list.addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayout))
    news_list
  }

  private val newsManager by lazy { NewsManager() }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return container?.inflate(R.layout.news_fragment)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    initAdapter()

    if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
      redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
      (newsList.adapter as NewsAdapter).clearAndAddNews(redditNews?.news)
    } else {
      requestNews()
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    val news = (newsList.adapter as NewsAdapter).getNews()
    if (redditNews != null && news.isNotEmpty()) {
      outState?.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
    }
  }

  private fun requestNews() {
    val subscription = newsManager.getNews(redditNews?.after ?: "")
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        retrieveNews ->
        redditNews = retrieveNews
        (newsList.adapter as NewsAdapter).addNews(retrieveNews.news)
      }, {
        e ->
        Snackbar.make(newsList, e.message ?: "", Snackbar.LENGTH_LONG).show()
      })
    subscriptions.add(subscription)

  }

  private fun initAdapter() {
    if (newsList.adapter == null) {
      newsList.adapter = NewsAdapter()
    }
  }
}

