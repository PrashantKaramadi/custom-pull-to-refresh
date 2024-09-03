package com.pk.custompulltorefresh

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pk.custompulltorefresh.views.CustomPullToRefreshLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val customPullToRefreshLayout =
            findViewById<CustomPullToRefreshLayout>(R.id.pullToRefreshLayout)

        // Customize ProgressBar
        customPullToRefreshLayout.setProgressBarColor(ContextCompat.getColor(this, R.color.black))
        customPullToRefreshLayout.setProgressBarSize(60) // Adjust size if needed
        customPullToRefreshLayout.setProgressBarStyle(1) // Use 0 for small, 1 for large

        customPullToRefreshLayout.setOnRefreshListener {
            // Handle refresh logic here
            customPullToRefreshLayout.postDelayed({
                // Simulate a network call
                customPullToRefreshLayout.resetRefreshView()
            }, 2000) // Simulate refresh duration
        }

    }
}