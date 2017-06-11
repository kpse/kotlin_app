package com.example.qsuo.kotlinapp.commons.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.qsuo.kotlinapp.commons.RedditNewsItem
import com.example.qsuo.kotlinapp.commons.adapter.LocalViewTypes.LOADING
import com.example.qsuo.kotlinapp.commons.adapter.LocalViewTypes.NEWS
import com.example.qsuo.kotlinapp.features.news.adapter.LoadingDelegateAdapter
import com.example.qsuo.kotlinapp.features.news.adapter.NewsDelegateAdapter

class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>
    private val delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem: ViewType = object: ViewType {
        override fun getViewType(): Int = LocalViewTypes.LOADING

    }

    init {
        delegateAdapter.put(LOADING, LoadingDelegateAdapter())
        delegateAdapter.put(NEWS, NewsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapter.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapter.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

    fun addNews(news: MutableList<RedditNewsItem>) {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(news)
        items.add(loadingItem)
        notifyItemChanged(initPosition, items.size + 1)
    }
}