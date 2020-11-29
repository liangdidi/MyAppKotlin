package com.ldd.mak.mvvm.model.network

import com.ldd.mak.mvvm.model.entity.LoginEntity
import io.reactivex.Observable
import retrofit2.http.*

/**
 * 接口
 */
interface ApiServer {

    companion object {
        const val BASE_URL = "http://oa.myqtyx.com/"
    }


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("app/sys/login")
    suspend fun login(@Tag tag: String, @FieldMap map: Map<String, String>): LoginEntity

}