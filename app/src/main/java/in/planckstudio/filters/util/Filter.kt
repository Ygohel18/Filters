package `in`.planckstudio.filters.util

import android.graphics.Bitmap

interface Filter {
    fun apply(src: Bitmap): Bitmap
}
