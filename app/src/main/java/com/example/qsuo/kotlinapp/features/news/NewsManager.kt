package com.example.qsuo.kotlinapp.features.news

import com.example.qsuo.kotlinapp.api.RestAPI
import com.example.qsuo.kotlinapp.commons.RedditNewsItem
import rx.Observable

class NewsManager(private val api: RestAPI = RestAPI()) {
  fun getNews(): Observable<List<RedditNewsItem>> {
    return Observable.create {
      subscriber ->
      val callResponse = api.getNews("", "10")
      val response = callResponse.execute()
      if (response.isSuccessful) {
        val news = response.body().data.children.map {
          val item = it.data
          RedditNewsItem(item.author, item.title, item.num_comments, item
            .created, item.thumbnail, item.url)
        }
        subscriber.onNext(news)
        subscriber.onCompleted()
      } else {
        subscriber.onError(Throwable(response.message()))
      }

    }
  }
}