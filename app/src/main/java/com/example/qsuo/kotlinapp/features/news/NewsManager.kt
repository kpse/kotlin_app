package com.example.qsuo.kotlinapp.features.news

import com.example.qsuo.kotlinapp.commons.RedditNewsItem
import rx.Observable

class NewsManager {
    fun getNews(): Observable<List<RedditNewsItem>> {
        return Observable.create {
            subscriber ->
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
            subscriber.onNext(news)
            subscriber.onCompleted()
        }
    }
}