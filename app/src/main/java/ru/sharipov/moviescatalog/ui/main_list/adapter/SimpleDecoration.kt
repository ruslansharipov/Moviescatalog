package ru.sharipov.moviescatalog.ui.main_list.adapter

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimpleDecoration(
    densityDpi: Int
) : RecyclerView.ItemDecoration() {

    companion object {
        private const val PADDING_BIG: Int = 16
        private const val PADDING_SMALL: Int = 8
    }

    private val small: Int
    private val inner: Int
    private val big: Int

    init {
        val coefficient = densityDpi / DisplayMetrics.DENSITY_DEFAULT
        big = PADDING_BIG * coefficient
        small = PADDING_SMALL * coefficient
        inner = small / 2
    }

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