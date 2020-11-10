package com.ldd.mak.mvvm.model.entity

class LoginEntity(
    val token: String,
    val refreshToken: String,
    val sign: String,
    val imId: String,
    var code: Int = 0,
    var msg: String? = null,
    var success: Boolean = false
)