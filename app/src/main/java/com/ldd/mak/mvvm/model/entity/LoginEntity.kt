package com.ldd.mak.mvvm.model.entity

import com.ldd.mak.mvvm.model._base.BaseEntity

class LoginEntity(
    val token: String,
    val refreshToken: String,
    val sign: String,
    val imId: String
): BaseEntity()