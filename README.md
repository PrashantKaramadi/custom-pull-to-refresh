
# CustomPullToRefreshLayout


CustomPullToRefreshLayout is a customizable pull-to-refresh layout for Android. It allows you to easily add pull-to-refresh functionality to your views with options to customize the progress bar appearance, size, and position.



## Features

- Customizable Progress Bar: Modify the color, size, and style of the progress bar.
- Adjustable Position: Set the position (gravity) of the progress bar.
- Custom Drawable: Option to set a custom drawable resource for the progress bar.
- Simple Integration: Easy to use with a straightforward listener for refresh events.


## Installation

Gradle
```bash
 dependencies {
    implementation("com.github.PrashantKaramadi:custom-pull-to-refresh:v1.0.2")
}
```
Usage
XML Layout
```bash
<com.pk.custompulltorefresh.CustomPullToRefreshLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:progressBarColor="@android:color/black"
    app:progressBarSize="48dp"
    app:progressBarStyle="0"   <!-- 0 for spinner, 1 for horizontal -->
    app:progressBarGravity="top|center_horizontal">

    <!-- Your content view goes here -->
</com.pk.custompulltorefresh.CustomPullToRefreshLayout>

```

Kotlin Usage
```bash
val customPullToRefresh = findViewById<CustomPullToRefreshLayout>(R.id.custom_pull_to_refresh)
customPullToRefresh.setOnRefreshListener {
    // Handle refresh logic here
}

customPullToRefresh.setMaxPullDownOffset(50f)
customPullToRefresh.setSpaceBetweenProgressBarAndContent(50f)
customPullToRefresh.setProgressBarColor(Color.RED)
customPullToRefresh.setProgressBarSize(64) // Size in dp
customPullToRefresh.setProgressBarStyle(1) // 0 for spinner, 1 for horizontal
customPullToRefresh.setProgressBarGravity(Gravity.BOTTOM or Gravity.END)
customPullToRefresh.setCustomProgressDrawable(R.drawable.custom_drawable)

```


https://github.com/user-attachments/assets/0f528686-b0b9-4d9f-8f12-76b6810d3f7b



## Custom Attributes
- setMaxPullDownOffset: Set maxPullDownOffset dynamically
- setSpaceBetweenProgressBarAndContent: Set space between ProgressBar and contentView dynamically
- progressBarColor: Set the color of the progress bar.
- progressBarSize: Set the size of the progress bar (in dp).
- progressBarStyle: Set the style of the progress bar (0 for spinner, 1 for horizontal).
- progressBarGravity: Set the gravity of the progress bar (e.g., Gravity.TOP | Gravity.CENTER_HORIZONTAL).
- customProgressDrawable: Set a custom drawable resource for the progress bar.
  
## Contributing

Feel free to open issues and submit pull requests. Please make sure to follow the contributing guidelines.


## Contact
For any inquiries, please contact prashant.karamadi31@gmail.com.

