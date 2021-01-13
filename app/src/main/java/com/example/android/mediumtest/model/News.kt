package com.example.android.mediumtest.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    @NonNull
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?
): Parcelable