package com.om.diucampusschedule.models

import com.om.diucampusschedule.R

object ImageNameMapper {
    private val imageNameMap = mapOf(
//        "41a" to R.drawable.room_41a,
//        "41b" to R.drawable.room_41b,
//        "41c" to R.drawable.room_41c,
//        "41d" to R.drawable.room_41d,
//        "41e" to R.drawable.room_41e,
//        "41f" to R.drawable.room_41f,
//        "41g" to R.drawable.room_41g,
//        "41h" to R.drawable.room_41h,
        "41i" to R.drawable.room_41i,
        "41j" to R.drawable.room_41j,
        "41k" to R.drawable.room_41k,
        "41l" to R.drawable.room_41l,
//        "41m" to R.drawable.room_41m,
//        "41n" to R.drawable.room_41n,
//        "41o" to R.drawable.room_41o,
//        "41p" to R.drawable.room_41p,

    )

    fun getResourseId(originalName: String): Int? {
        return imageNameMap[originalName.lowercase()]
    }
}