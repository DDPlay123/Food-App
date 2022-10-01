package com.side.project.foodapp.utils

import android.content.Context
import android.widget.Toast

fun Context.displayToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()