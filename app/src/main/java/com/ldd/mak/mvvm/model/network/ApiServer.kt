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
    fun login(@Tag tag: String, @FieldMap map: Map<String, String>): Observable<LoginEntity>
    /**
     * 登录
     */
    @POST("app/sys/login")
    fun login(
        @Tag tag: String,
        @Query("userName") userName: String,
        @Query("password") password: String
    ): Observable<LoginEntity>

    /**
     * 获取客户
     */
    @GET("/app/crmCustomerBasicInfo/list")
    fun getKeHu(@Tag tag: String): Observable<LoginEntity>

    /**
     * 获取新闻数据
     * @param type 类型
     * @param key appkey
     * @return
     */
    @GET("toutiao/index")
    fun getNews(@Query("type") type: String, @Query("key") key: String): Observable<LoginEntity>
}