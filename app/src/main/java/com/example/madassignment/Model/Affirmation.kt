package com.example.madassignment.Model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
        @StringRes val stringResourceId: Int,
        @DrawableRes val imageResourceId: Int,
        @DrawableRes val stockStatusIcon : Int,
        @StringRes var stockStatus: Int,
        val price : Double
)


