package com.ldd.mak.mvvm.model.network

import android.util.Log
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 */
class RetrofitUtil {
    private val TAG = RetrofitUtil::class.java.simpleName
    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient

    init {
        Log.i(TAG, "=======RetrofitUtil初始化=======")
        //公共参数 和 header
//        val commonParams= Interceptor { chain: Interceptor.Chain ->
//            val request = chain.request()
//
//            val httpUrl = request.url()
//                .newBuilder()
//                .addQueryParameter("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDUwODYwODMsInVzZXJuYW1lIjoiMjAwMjAifQ.tXmdhTAFSGMJ2FsfrIFLMY8CK3z4QNw5RhAifTzDUhg")
//                .build()
//
//            val build: Request = request.newBuilder()
//                .addHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDUwODYwODMsInVzZXJuYW1lIjoiMjAwMjAifQ.tXmdhTAFSGMJ2FsfrIFLMY8CK3z4QNw5RhAifTzDUhg")
//                .url(httpUrl)
//                .build()
//
//            val response = chain.proceed(build)
//            response
//        }

        //打印请求响应日志
        val logPrint =Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                val response = chain.proceed(request)
                printLog(request, response)
                response
            }
        okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(commonParams)
            .addInterceptor(logPrint)
            .proxy(Proxy.NO_PROXY)
            .connectTimeout(
                connectTimeout.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                readTimeout.toLong(),
                TimeUnit.SECONDS
            )
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(ApiServer.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        apiService = retrofit.create(ApiServer::class.java)
    }

    fun getApiService(): ApiServer {
        return apiService
    }

    companion object {
        var connectTimeout = 30
        var readTimeout = 20
        private lateinit var apiService: ApiServer
        private var instance:RetrofitUtil?=null
        fun getInstance(): RetrofitUtil {
            if (instance == null) {
                synchronized(RetrofitUtil::class.java) {
                    if (instance == null) {
                        instance = RetrofitUtil()
                    }
                }
            }
            return instance!!
        }

        /**
         * 设置null，用于重新配置retrofit配置，base_url,拦截器
         */
        fun setInstance(mInstance: RetrofitUtil?){
            instance=mInstance
        }
    }

    //===========================================打印请求，响应 信息==============================================
    /**拼接请求，响应 信息 */
    private val mLogInfo = StringBuilder()

    /**
     * 打印请求，响应
     */
    @Synchronized
    private fun printLog(request: Request, response: Response) {
        val tagName=request.tag(String::class.java)
        mLogInfo.setLength(0)
        mLogInfo.append("接口名称：").append(tagName)
            .append("\n")
        mLogInfo.append("请求方式：").append(request.method()).append("\n")
        mLogInfo.append("请求链接：")
        val requestUrl = request.url().toString()
        if (requestUrl.contains("?")) {
            val url = requestUrl.substring(0, requestUrl.indexOf("?"))
            val param = requestUrl.substring(requestUrl.indexOf("?") + 1)
            mLogInfo.append(url).append("\n")
            val arrayParam = param.split("&".toRegex()).toTypedArray()
            for (i in arrayParam.indices) {
                mLogInfo.append("　　　　　")
                if (i == 0) {
                    mLogInfo.append("?")
                } else {
                    mLogInfo.append("&")
                }
                mLogInfo.append(arrayParam[i]).append("\n")
            }
        } else {
            mLogInfo.append(requestUrl).append("\n")
        }


        //如有有头部
        if(request.headers().names().isNotEmpty()){
            mLogInfo.append("请求头部：")
            for ((indent, name) in request.headers().names().withIndex()) {
                if (indent != 0) {
                    mLogInfo.append("　　　　　")
                }
                mLogInfo.append(name).append("=").append(request.headers().get(name)).append("\n")
            }
        }


        if ("POST" == request.method()) {
            if (request.body() is FormBody) {
                mLogInfo.append("请求参数：")
                val formBody = request.body() as FormBody
                for (i in 0 .. formBody.size()) {
                    val name = formBody.encodedName(i)
                    val value = formBody.value(i)
                    if (i != 0) {
                        mLogInfo.append("　　　　　")
                    }
                    mLogInfo.append(name).append("=").append(value).append("\n")
                }
            } else if (request.body() is MultipartBody) {
                mLogInfo.append("请求参数（上传文件操作，暂时不能打印请求参数>_<）：").append("\n")
            }
        }
        mLogInfo.append("响应耗时：")
        mLogInfo.append(formatDuring(response.receivedResponseAtMillis() - response.sentRequestAtMillis()))
        mLogInfo.append("\n")

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response.peekBody(1024 * 1024.toLong())
        mLogInfo.append("响应数据：")
        mLogInfo.append("\n")
        mLogInfo.append(formatJson(responseBody.string()))
        Logger.t("$TAG-$tagName")
        Logger.d(mLogInfo.toString())
    }

    /**
     * 格式化json字符串
     */
    private fun formatJson(jsonStr: String?): String {
        if (null == jsonStr || "" == jsonStr) return ""
        val sb = StringBuilder()
        var last = '\u0000'
        var current = '\u0000'
        var indent = 0
        for (element in jsonStr) {
            last = current
            current = element
            when (current) {
                '{', '[' -> {
                    sb.append(current)
                    sb.append('\n')
                    indent++
                    addIndentBlank(sb, indent)
                }
                '}', ']' -> {
                    sb.append('\n')
                    indent--
                    addIndentBlank(sb, indent)
                    sb.append(current)
                }
                ',' -> {
                    sb.append(current)
                    if (last != '\\') {
                        sb.append('\n')
                        addIndentBlank(sb, indent)
                    }
                }
                else -> sb.append(current)
            }
        }
        return sb.toString()
    }
    /**
     * 添加space
     */
    private fun addIndentBlank(sb: StringBuilder, indent: Int) {
        for (i in 0 until indent) {
            sb.append('\t')
        }
    }

    /**
     * 格式化时间
     */
    private fun formatDuring(ms: Long): String {
        return when {
            ms < 1000 -> "${ms}ms"
            ms < 1000 * 60 -> "${ms % (1000 * 60) / 1000}s"
            else -> "${ms % (1000 * 60 * 60) / (1000 * 60)}min"
        }
    }
}