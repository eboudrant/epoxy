package com.airbnb.epoxy

import androidx.annotation.IntDef

object VisibilityState {

    /**
     * Event triggered when a Component enters the Visible Range. This happens when at least a pixel
     * of the Component is visible.
     */
    const val VISIBLE = 0

    /**
     * Event triggered when a Component becomes invisible. This is the same with exiting the Visible
     * Range, the Focused Range and the Full Impression Range. All the code that needs to be executed
     * when a component leaves any of these ranges should be written in the handler for this event.
     */
    const val INVISIBLE = 1

    /**
     * Event triggered when a Component enters the Focused Range. This happens when either the
     * Component occupies at least half of the viewport or, if the Component is smaller than half of
     * the viewport, when the it is fully visible.
     */
    const val FOCUSED_VISIBLE = 2

    /**
     * Event triggered when a Component exits the Focused Range. The Focused Range is defined as at
     * least half of the viewport or, if the Component is smaller than half of the viewport, when the
     * it is fully visible.
     */
    const val UNFOCUSED_VISIBLE = 3

    /**
     * Event triggered when a Component enters the Full Impression Range. This happens, for instance
     * in the case of a vertical RecyclerView, when both the top and bottom edges of the component
     * become visible.
     */
    const val FULL_IMPRESSION_VISIBLE = 4

    @Retention
    @IntDef(VISIBLE, INVISIBLE, FOCUSED_VISIBLE, UNFOCUSED_VISIBLE, FULL_IMPRESSION_VISIBLE)
    annotation class Visibility
}
