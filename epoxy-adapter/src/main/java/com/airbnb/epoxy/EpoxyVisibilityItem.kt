package com.airbnb.epoxy

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

/**
 * This class represent an item in the [androidx.recyclerview.widget.RecyclerView] and it is
 * being reused with multiple model via the update method. There is 1:1 relationship between an
 * EpoxyVisibilityItem and a child within the [androidx.recyclerview.widget.RecyclerView].
 *
 * It contains the logic to compute the visibility state of an item. It will also invoke the
 * visibility callbacks on [com.airbnb.epoxy.EpoxyViewHolder]
 *
 * This class should remain non-public and is intended to be used by [EpoxyVisibilityTracker]
 * only.
 */
internal class EpoxyVisibilityItem(adapterPosition: Int) {

    private val localVisibleRect = Rect()

    var adapterPosition = RecyclerView.NO_POSITION
        private set

    @Px
    private var height: Int = 0
    @Px
    private var width: Int = 0

    @Px
    private var visibleHeight: Int = 0
    @Px
    private var visibleWidth: Int = 0

    @Px
    private var viewportHeight: Int = 0
    @Px
    private var viewportWidth: Int = 0

    private var fullyVisible = false
    private var visible = false
    private var focusedVisible = false

    /** Store last value for de-duping  */
    private var lastVisibleHeightNotified: Int? = null
    private var lastVisibleWidthNotified: Int? = null

    init {
        reset(adapterPosition)
    }

    /**
     * Update the visibility item according the current layout.
     *
     * @param view the current [com.airbnb.epoxy.EpoxyViewHolder]'s itemView
     * @param parent the [androidx.recyclerview.widget.RecyclerView]
     * @return true if the view has been measured
     */
    fun update(view: View, parent: RecyclerView, detachEvent: Boolean): Boolean {
        // Clear the rect before calling getLocalVisibleRect
        localVisibleRect.setEmpty()
        val viewDrawn = view.getLocalVisibleRect(localVisibleRect) && !detachEvent
        height = view.height
        width = view.width
        viewportHeight = parent.height
        viewportWidth = parent.width
        visibleHeight = if (viewDrawn) localVisibleRect.height() else 0
        visibleWidth = if (viewDrawn) localVisibleRect.width() else 0
        return height > 0 && width > 0
    }

    fun reset(newAdapterPosition: Int) {
        fullyVisible = false
        visible = false
        focusedVisible = false
        adapterPosition = newAdapterPosition
        lastVisibleHeightNotified = null
        lastVisibleWidthNotified = null
    }

    fun handleVisible(epoxyHolder: EpoxyViewHolder, detachEvent: Boolean) {
        val previousVisible = visible
        visible = !detachEvent && isVisible()
        if (visible != previousVisible) {
            if (visible) {
                epoxyHolder.visibilityStateChanged(VisibilityState.VISIBLE)
            } else {
                epoxyHolder.visibilityStateChanged(VisibilityState.INVISIBLE)
            }
        }
    }

    fun handleFocus(epoxyHolder: EpoxyViewHolder, detachEvent: Boolean) {
        val previousFocusedVisible = focusedVisible
        focusedVisible = !detachEvent && isInFocusVisible()
        if (focusedVisible != previousFocusedVisible) {
            if (focusedVisible) {
                epoxyHolder.visibilityStateChanged(VisibilityState.FOCUSED_VISIBLE)
            } else {
                epoxyHolder.visibilityStateChanged(VisibilityState.UNFOCUSED_VISIBLE)
            }
        }
    }

    fun handleFullImpressionVisible(epoxyHolder: EpoxyViewHolder, detachEvent: Boolean) {
        val previousFullyVisible = fullyVisible
        fullyVisible = !detachEvent && isFullyVisible()
        if (fullyVisible != previousFullyVisible) {
            if (fullyVisible) {
                epoxyHolder.visibilityStateChanged(VisibilityState.FULL_IMPRESSION_VISIBLE)
            }
        }
    }

    fun handleChanged(epoxyHolder: EpoxyViewHolder, visibilityChangedEnabled: Boolean): Boolean {
        var changed = false
        if (visibleHeight != lastVisibleHeightNotified || visibleWidth != lastVisibleWidthNotified) {
            if (visibilityChangedEnabled) {
                epoxyHolder.visibilityChanged(
                    100f / height * visibleHeight,
                    100f / width * visibleWidth,
                    visibleHeight, visibleWidth
                )
            }
            lastVisibleHeightNotified = visibleHeight
            lastVisibleWidthNotified = visibleWidth
            changed = true
        }
        return changed
    }

    fun shiftBy(offsetPosition: Int) {
        adapterPosition += offsetPosition
    }

    private fun isVisible() = visibleHeight > 0 && visibleWidth > 0

    private fun isInFocusVisible(): Boolean {
        val halfViewportArea = viewportHeight * viewportWidth / 2
        val totalArea = height * width
        val visibleArea = visibleHeight * visibleWidth
        // The model has entered the focused range either if it is larger than half of the viewport
        // and it occupies at least half of the viewport or if it is smaller than half of the viewport
        // and it is fully visible.
        return if (totalArea >= halfViewportArea)
            visibleArea >= halfViewportArea
        else
            totalArea == visibleArea
    }

    private fun isFullyVisible() = visibleHeight == height && visibleWidth == width
}
