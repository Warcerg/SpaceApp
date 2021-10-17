package com.example.spaceapp

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.UnderlineSpan
import java.text.SimpleDateFormat
import java.util.*

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun getYesterdayDateTime(): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    return calendar.time
}

fun getDayBeforeYesterdayDateTime(): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -2)
    return calendar.time
}

fun spanHighlightFirstWord(string: String, char: Char): SpannableString {
    val spannableString = SpannableString(string)
    spannableString.setSpan(
        ForegroundColorSpan(Color.RED), 0, spannableString.indexOf(char),
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    spannableString.setSpan(
        RelativeSizeSpan(1.5f), 0, spannableString.indexOf(char),
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    spannableString.setSpan(
        UnderlineSpan(), 0, spannableString.indexOf(char),
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    return spannableString
}

