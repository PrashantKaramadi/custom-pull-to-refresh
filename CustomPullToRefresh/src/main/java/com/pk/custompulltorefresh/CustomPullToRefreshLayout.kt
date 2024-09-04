package com.pk.custompulltorefresh

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.ViewCompat

class CustomPullToRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val refreshView: ProgressBar
    private var contentView: View? = null
    private var isRefreshing = false
    private var onRefreshListener: (() -> Unit)? = null
    private val gestureDetector: GestureDetector

    // Default values for maximum pull-down offset and space between ProgressBar and contentView
    private var maxPullDownOffset = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        50f,
        resources.displayMetrics
    )
    private var spaceBetweenProgressBarAndContent = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        20f,
        resources.displayMetrics
    )

    init {
        // Inflate the custom view layout and initialize the refreshView and gestureDetector
        inflate(context, R.layout.view_custom_pull_to_refresh, this)
        refreshView = findViewById(R.id.refresh_view)
        gestureDetector = GestureDetector(context, RefreshGestureListener())
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // Automatically set the second child view as the content view (assuming the first is the ProgressBar)
        if (childCount > 1) {
            val child = getChildAt(1) // Get the actual content, ignoring the ProgressBar
            setInnerContentView(child)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // Intercept touch events only if not refreshing and the gesture detector detects a scroll
        val shouldIntercept = !isRefreshing && gestureDetector.onTouchEvent(ev)
        return shouldIntercept && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Process touch events through the gesture detector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private inner class RefreshGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true // Always return true to indicate that we want to receive the touch events
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (!isRefreshing) {
                // Check if the contentView can scroll vertically
                val canScroll = ViewCompat.canScrollVertically(contentView, -1)
                if (!canScroll && e1 != null && e2.y - e1.y > 0) {
                    // Calculate the offset and cap it to the max pull-down offset
                    var offset = e2.y - e1.y
                    if (offset > maxPullDownOffset) {
                        offset = maxPullDownOffset
                    }

                    // Move the content view down and maintain space between ProgressBar and contentView
                    contentView?.translationY = offset
                    refreshView.translationY = offset - spaceBetweenProgressBarAndContent

                    // Start refreshing if the offset reaches the maxPullDownOffset
                    if (offset == maxPullDownOffset) {
                        startRefreshing()
                        return true
                    }
                }
            }
            return false
        }
    }

    private fun startRefreshing() {
        isRefreshing = true
        post {
            // Make the ProgressBar visible and force a layout update
            refreshView.visibility = VISIBLE
            requestLayout()
        }
        // Notify the listener about the refresh event
        onRefreshListener?.invoke()
    }

    fun resetRefreshView() {
        isRefreshing = false
        post {
            // Animate the contentView and ProgressBar back to their original positions
            contentView?.animate()?.translationY(0f)?.setDuration(300)?.start()
            refreshView.animate()?.translationY(0f)?.setDuration(300)?.start()
            // Hide the ProgressBar and reset its alpha
            refreshView.visibility = GONE
            refreshView.alpha = 1f
            requestLayout()
        }
    }

    fun setOnRefreshListener(listener: () -> Unit) {
        onRefreshListener = listener
    }

    private fun setInnerContentView(view: View) {
        contentView = view
    }

    // Method to set maxPullDownOffset dynamically
    fun setMaxPullDownOffset(dp: Float) {
        maxPullDownOffset = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
    }

    // Method to set space between ProgressBar and contentView dynamically
    fun setSpaceBetweenProgressBarAndContent(dp: Float) {
        spaceBetweenProgressBarAndContent = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
    }
}
