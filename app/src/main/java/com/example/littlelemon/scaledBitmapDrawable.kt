package com.example.littlelemon

import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
fun scaledBitmapDrawable(
    @DrawableRes resId: Int,
    reqWidth: Int = 500,
    reqHeight: Int = 500
): ImageBitmap {
    val context = LocalContext.current
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, resId, this)

        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
        inJustDecodeBounds = false
    }

    val bitmap = BitmapFactory.decodeResource(context.resources, resId, options)
    return bitmap.asImageBitmap()
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}
