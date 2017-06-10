package com.example.qsuo.kotlinapp.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.qsuo.kotlinapp.R
import com.example.qsuo.kotlinapp.commons.RedditNewsItem
import com.example.qsuo.kotlinapp.commons.adapter.ViewType
import com.example.qsuo.kotlinapp.commons.adapter.ViewTypeDelegateAdapter
import com.example.qsuo.kotlinapp.commons.inflate
import com.example.qsuo.kotlinapp.commons.loadImg
import kotlinx.android.synthetic.main.news_item.view.*


class NewsDelegateAdapter: ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as NewsViewHolder
        holder.bind(item as RedditNewsItem)
    }
    class NewsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
            parent.inflate(R.layout.news_item)) {
        fun bind(item: RedditNewsItem) = with(itemView) {
            img_thumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numberComments} comments"
            time.text = "${item.created}"
        }
    }
}