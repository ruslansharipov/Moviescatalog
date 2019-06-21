package ru.sharipov.moviescatalog.ui.main_list.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimpleDecoration(
    private val small: Int,
    private val inner: Int,
    private val big: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val itemCount = parent.layoutManager?.itemCount
        val rect = when {
            position == 0 -> Rect(small, big, small, inner)
            position + 1 == itemCount -> Rect(small, inner, small, big)
            else -> Rect(small, inner, small, inner)
        }
        outRect.set(rect)
    }
}