package com.eltex.androidschool.adapter

import android.graphics.Rect
import android.view.View
import  androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class OffsetDecoration(
    @Px private val offset: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top += offset
        outRect.left += offset
        outRect.right += offset
        val lastIndex = parent.adapter?.itemCount?.minus(1)
        val lastViewElement =
            (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (lastIndex == lastViewElement) {
            outRect.bottom += offset
        }
    }
}