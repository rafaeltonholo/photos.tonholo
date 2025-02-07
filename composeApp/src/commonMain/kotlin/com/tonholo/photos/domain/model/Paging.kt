package com.tonholo.photos.domain.model

import kotlin.jvm.JvmInline

data class Paging(
    val count: Int,
    val next: Cursor?,
    val prev: Cursor?,
)

@JvmInline
value class Cursor(val value: String)
