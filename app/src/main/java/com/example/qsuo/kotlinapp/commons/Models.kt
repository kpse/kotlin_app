package com.example.qsuo.kotlinapp.commons

import com.example.qsuo.kotlinapp.commons.adapter.LocalViewTypes.NEWS
import com.example.qsuo.kotlinapp.commons.adapter.ViewType

data class RedditNews(
  val after: String,
  val before: String,
  val news: List<RedditNewsItem>
)

data class RedditNewsItem(
  val author: String,
  val title: String,
  val numberComments: Int,
  val created: Long,
  val thumbnail: String,
  val url: String
) : ViewType {
  override fun getViewType(): Int = NEWS
}