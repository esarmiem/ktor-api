package com.esarmiem.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: Int, val name: String, val age: Int ,val email: String)