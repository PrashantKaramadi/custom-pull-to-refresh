CustomPullToRefreshLayout
CustomPullToRefreshLayout is a customizable pull-to-refresh layout for Android. It allows you to easily add pull-to-refresh functionality to your views with options to customize the progress bar appearance, size, and position.

Features
Customizable progress bar color, size, and style.
Adjustable position (gravity) of the progress bar.
Option to set a custom drawable for the progress bar.
Easy integration with a simple listener for refresh events.

Installation
Gradle
Add the following dependency to your build.gradle file:

dependencies {
    implementation 'com.pk:custompulltorefresh:1.0.0'
}

repositories {
    google()
    mavenCentral()
}

Usage
XML Layout
To use CustomPullToRefreshLayout in your XML layout, add it as follows:

<com.pk.custompulltorefresh.CustomPullToRefreshLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:progressBarColor="@android:color/black"
    app:progressBarSize="48dp"
    app:progressBarStyle="0"   <!-- 0 for spinner, 1 for horizontal -->
    app:progressBarGravity="top|center_horizontal">

    <!-- Your content view goes here -->
</com.pk.custompulltorefresh.CustomPullToRefreshLayout>

//.kts

val customPullToRefresh = findViewById<CustomPullToRefreshLayout>(R.id.custom_pull_to_refresh)
customPullToRefresh.setOnRefreshListener {
    // Handle refresh logic here
}

customPullToRefresh.setProgressBarColor(Color.RED)
customPullToRefresh.setProgressBarSize(64) // Size in dp
customPullToRefresh.setProgressBarStyle(1) // 0 for spinner, 1 for horizontal
customPullToRefresh.setProgressBarGravity(Gravity.BOTTOM or Gravity.END)
customPullToRefresh.setCustomProgressDrawable(R.drawable.custom_drawable)


Custom Attributes

progressBarColor: Set the color of the progress bar.
progressBarSize: Set the size of the progress bar (in dp).
progressBarStyle: Set the style of the progress bar (0 for spinner, 1 for horizontal).
progressBarGravity: Set the gravity of the progress bar (e.g., Gravity.TOP | Gravity.CENTER_HORIZONTAL).
customProgressDrawable: Set a custom drawable resource for the progress bar.


Contributing
Feel free to open issues and submit pull requests. Please make sure to follow the contributing guidelines.

Contact
For any inquiries, please contact prashant.karamadi31@gmail.com
