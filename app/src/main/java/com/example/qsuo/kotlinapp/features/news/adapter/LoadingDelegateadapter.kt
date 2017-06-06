package com.example.qsuo.kotlinapp.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.qsuo.kotlinapp.R
import com.example.qsuo.kotlinapp.commons.adapter.ViewType
import com.example.qsuo.kotlinapp.commons.adapter.ViewTypeDelegateAdapter
import com.example.qsuo.kotlinapp.commons.inflate


class LoadingDelegateadapter : ViewTypeDelegateAdapter {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TurnsViewHolder(parent)

}

class TurnsViewHolder(parent: ViewGroup): RecyclerView.ViewHolder (
        parent.inflate(R.layout.news_item_loading)
) {

}

