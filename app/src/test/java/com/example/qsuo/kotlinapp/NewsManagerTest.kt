package com.example.qsuo.kotlinapp

import com.example.qsuo.kotlinapp.api.NewsAPI
import com.example.qsuo.kotlinapp.api.RedditChildrenResponse
import com.example.qsuo.kotlinapp.api.RedditDataResponse
import com.example.qsuo.kotlinapp.api.RedditNewsDataResponse
import com.example.qsuo.kotlinapp.api.RedditNewsResponse
import com.example.qsuo.kotlinapp.commons.RedditNews
import com.example.qsuo.kotlinapp.features.news.NewsManager
import com.nhaarman.mockito_kotlin.mock
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import retrofit2.Call
import retrofit2.Response
import rx.observers.TestSubscriber
import java.util.*

class NewsManagerTest {
  var testSub = TestSubscriber<RedditNews>()
  var apiMock = mock<NewsAPI>()
  var callMock = mock<Call<RedditNewsResponse>>()

  @Before
  fun setUp() {
    testSub = TestSubscriber<RedditNews>()
    apiMock = mock<NewsAPI>()
    callMock = mock<Call<RedditNewsResponse>>()

    `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
  }

  @Test
  fun shouldSuccess() {
    val newsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
    val response = Response.success(newsResponse)
    `when`(callMock.execute()).thenReturn(response)

    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)

    testSub.assertNoErrors()
    testSub.assertValueCount(1)
    testSub.assertCompleted()
  }

  @Test
  fun shouldCheckOneNews() {
    val newsData = RedditNewsDataResponse(
      "author",
      "title",
      10,
      Date().time,
      "thumbnail",
      "url"
    )

    val newsResponse = RedditNewsResponse(RedditDataResponse(listOf(RedditChildrenResponse(newsData)), null, null))
    val response = Response.success(newsResponse)
    `when`(callMock.execute()).thenReturn(response)

    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)

    testSub.assertNoErrors()
    testSub.assertValueCount(1)
    testSub.assertCompleted()

    assert(testSub.onNextEvents[0].news[0].author == newsData.author)
    assert(testSub.onNextEvents[0].news[0].title == newsData.title)
  }

  @Test
  fun shouldReportError() {
    val errorResponse = Response.error<RedditNewsResponse>(500, ResponseBody.create(MediaType.parse("application/json"), ""))
    `when`(callMock.execute()).thenReturn(errorResponse)

    val newsManager = NewsManager(apiMock)
    newsManager.getNews("").subscribe(testSub)

    assert(testSub.onErrorEvents.size == 1)

  }
}