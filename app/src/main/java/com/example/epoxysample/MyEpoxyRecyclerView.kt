package com.example.epoxysample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView

class MyEpoxyRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EpoxyRecyclerView(context, attrs, defStyleAttr) {

    private var lastExecuted: Long = 0

    private val update = Runnable {
        val now = System.currentTimeMillis()
        Log.d("MyEpoxyRecyclerView", "update !!!!!!!!!! ${now - lastExecuted}")
        lastExecuted = now
    }

    init {
        viewTreeObserver.addOnGlobalLayoutListener {
            Log.d("MyEpoxyRecyclerView", "addOnGlobalLayoutListener")
            invalidateUpdate()
        }
        viewTreeObserver.addOnPreDrawListener {
            Log.d("MyEpoxyRecyclerView", "addOnPreDrawListener")
            invalidateUpdate()
            true
        }
        viewTreeObserver.addOnDrawListener {
            Log.d("MyEpoxyRecyclerView", "addOnDrawListener")
            invalidateUpdate()
        }
        addItemDecoration(object : ItemDecoration() {

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                Log.d("MyEpoxyRecyclerView", "ItemDecoration.getItemOffsets")
                invalidateUpdate()
                super.getItemOffsets(outRect, view, parent, state)
            }
            override fun onDraw(c: Canvas, parent: RecyclerView, state: State) {
                Log.d("MyEpoxyRecyclerView", "ItemDecoration.onDraw")
                invalidateUpdate()
            }
        })
    }

    private fun invalidateUpdate() {
        removeCallbacks(update)
        postDelayed(update, 16)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d("MyEpoxyRecyclerView", "onLayout changed=$changed")
        super.onLayout(changed, l, t, r, b)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        Log.d("MyEpoxyRecyclerView", "onMeasure")
        super.onMeasure(widthSpec, heightSpec)
    }

    override fun onDraw(c: Canvas?) {
        Log.d("MyEpoxyRecyclerView", "onDraw")
        super.onDraw(c)
    }
}