package com.pk.custompulltorefresh.views


import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import com.pk.custompulltorefresh.R

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

    private var progressBarGravity: Int = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    private var progressBarColor: Int = Color.BLACK
    private var progressBarSize: Int = 48 // Default size in dp
    private var progressBarStyle: Int = 0 // Use 0 for spinner and 1 for horizontal
    private var customProgressDrawableRes: Int? = null // Custom drawable resource

    init {
        inflate(context, R.layout.view_custom_pull_to_refresh, this)
        refreshView = findViewById(R.id.refresh_view)
        gestureDetector = GestureDetector(context, RefreshGestureListener())
        // Apply default settings
        applyProgressBarAttributes()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        // Automatically set the first child view as the content view
        if (childCount > 0) {
            val child = getChildAt(0)
            setInnerContentView(child)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // Only intercept touch events if we are not refreshing and the gesture detector detects a scroll
        val shouldIntercept = !isRefreshing && gestureDetector.onTouchEvent(ev)
        return shouldIntercept && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Process touch events through the gesture detector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private inner class RefreshGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (!isRefreshing) {
                val canScroll = ViewCompat.canScrollVertically(contentView, -1)
                if (!canScroll && e1 != null && e2.y - e1.y > refreshView.height) {
                    startRefreshing()
                    return true
                }
            }
            return false
        }
    }

    private fun startRefreshing() {
        isRefreshing = true
        refreshView.visibility = VISIBLE
        post {
            // Center the ProgressBar programmatically if needed
            applyProgressBarAttributes()
            // Force layout update
            requestLayout()
        }
        onRefreshListener?.invoke()
    }

    fun resetRefreshView() {
        isRefreshing = false
        refreshView.visibility = GONE
        post {
            // Force layout update
            requestLayout()
        }
    }

    fun setOnRefreshListener(listener: () -> Unit) {
        onRefreshListener = listener
    }

    private fun applyProgressBarAttributes() {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = progressBarGravity
        refreshView.layoutParams = layoutParams
        // Apply color filter
        refreshView.indeterminateDrawable?.let {
            it.setColorFilter(progressBarColor, android.graphics.PorterDuff.Mode.SRC_IN)
        }

        // Set size
        val density = context.resources.displayMetrics.density
        val sizeInPx = (progressBarSize * density).toInt()
        refreshView.layoutParams.width = sizeInPx
        refreshView.layoutParams.height = sizeInPx
        refreshView.requestLayout()
    }

    fun setProgressBarColor(color: Int) {
        progressBarColor = color
        applyProgressBarAttributes()
    }

    fun setProgressBarSize(size: Int) {
        progressBarSize = size
        applyProgressBarAttributes()
    }

    fun setProgressBarStyle(style: Int) {
        progressBarStyle = style
        applyProgressBarAttributes()
    }

    fun setProgressBarGravity(gravity: Int) {
        progressBarGravity = gravity
        applyProgressBarAttributes()
    }

    fun setCustomProgressDrawable(drawableRes: Int) {
        customProgressDrawableRes = drawableRes
        applyProgressBarAttributes()
    }

    private fun setInnerContentView(view: View) {
        contentView = view
    }
}
