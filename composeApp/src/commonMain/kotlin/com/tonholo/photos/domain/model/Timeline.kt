package com.tonholo.photos.domain.model

data class Timeline(
    val photos: List<Photo>,
    val paging: Paging,
)
