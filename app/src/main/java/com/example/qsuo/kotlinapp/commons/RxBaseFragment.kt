package com.example.qsuo.kotlinapp.commons

import android.support.v4.app.Fragment
import rx.subscriptions.CompositeSubscription

open class RxBaseFragment : Fragment() {
  protected var subscriptions = CompositeSubscription()

  override fun onResume() {
    super.onResume()
    subscriptions = CompositeSubscription()
  }

  override fun onPause() {
    super.onPause()
    subscriptions.clear()
  }
}
