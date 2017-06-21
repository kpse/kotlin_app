package com.example.qsuo.kotlinapp.api

import retrofit2.Call

interface NewsAPI {
  fun getNews(after: String, limit: String): Call<RedditNewsResponse>
}
