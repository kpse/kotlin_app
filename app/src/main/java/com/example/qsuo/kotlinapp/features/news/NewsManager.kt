package com.example.qsuo.kotlinapp.features.news

import com.example.qsuo.kotlinapp.api.NewsAPI
import com.example.qsuo.kotlinapp.api.NewsRestAPI
import com.example.qsuo.kotlinapp.commons.RedditNews
import com.example.qsuo.kotlinapp.commons.RedditNewsItem
import rx.Observable

class NewsManager(private val restApi: NewsAPI = NewsRestAPI()) {
  fun getNews(after: String, limit: String = "10"): Observable<RedditNews> {
    return Observable.create {
      subscriber ->
      val callResponse = restApi.getNews(after, limit)
      val response = callResponse.execute()
      if (response.isSuccessful) {
        val dataResponse = response.body().data
        val news = dataResponse.children.map {
          val item = it.data
          RedditNewsItem(item.author, item.title, item.num_comments, item
            .created, item.thumbnail, item.url)
        }
        val redditNews = RedditNews(
          dataResponse.after ?: "",
          dataResponse.before ?: "",
          news
        )
        subscriber.onNext(redditNews)
        subscriber.onCompleted()
      } else {
        subscriber.onError(Throwable(response.message()))
      }

    }
  }
}