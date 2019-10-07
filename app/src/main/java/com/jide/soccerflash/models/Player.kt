package com.jide.soccerflash.models

data class Player(
    val id: Int,
    val name: String?,
    val firstName: String?,
    val lastName: String?,
    val dateOfBirth: String?,
    val countryOfBirth: String?,
    val nationality: String?,
    val position: String?,
    val lastUpdated: String?
)