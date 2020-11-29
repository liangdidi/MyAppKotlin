package com.ldd.mak.mvvm.model.network;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Invocation;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 时间： 2016/12/27.13:56
 * 描述：
 * 来源：
 */
public class RetrofitUtil {

    public static RetrofitUtil retrofitUtil;
    private Retrofit retrofit;
    private OkHttpClient client;
    private ApiServer apiServer;
    private String TAG = "retrofitUtil";


    private Interceptor headInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .addQueryParameter("token", "")
                    .build();

            Request build = request.newBuilder()
                    .addHeader("clienttype", "1")
                    .url(httpUrl)
                    .build();

            return chain.proceed(build);
        }
    };

    public static int connectTimeout=30;
    public static int readTimeout=30;

    private RetrofitUtil() {

        //打印请求响应日志
        Interceptor logPrint = chain -> {
            Request request=chain.request();
            Response response = chain.proceed(request);
            printLog(request,response);
            return response;
        };

        client = new OkHttpClient.Builder()
                //添加log拦截器
                .addInterceptor(headInterceptor)
                .addInterceptor(logPrint)
                .proxy(Proxy.NO_PROXY)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiServer = retrofit.create(ApiServer.class);
    }

    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            synchronized (Object.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }

    public ApiServer getApiService() {
        return apiServer; 
    }



    //========================================拼接打印网络请求========================================================
    /**拼接请求，响应 信息*/
    private StringBuilder mLogInfo= new StringBuilder();
    /**
     * 打印请求，响应
     */
    private synchronized void printLog(Request request,Response response)throws IOException {
//        Log.i("ldd","=======request========"+new Gson().toJson(request));
//        Log.i("ldd","=======response========"+new Gson().toJson(response));
            mLogInfo.setLength(0);

            String tag=request.tag(String.class);
            mLogInfo.append("接口名称：").append(tag).append("\n");
            mLogInfo.append("请求方式：").append(request.method()).append("\n");
            mLogInfo.append("请求链接：");
            String requestUrl=request.url().toString();
            if(requestUrl.contains("?")){
                String url=requestUrl.substring(0,requestUrl.indexOf("?"));
                String param=requestUrl.substring(requestUrl.indexOf("?")+1);
                mLogInfo.append(url).append("\n");

                String[] arrayParam=param.split("&");
                for (int i = 0; i < arrayParam.length; i++) {
                    mLogInfo.append("　　　　　");
                    if(i==0){
                        mLogInfo.append("?");
                    }else{
                        mLogInfo.append("&");
                    }
                    mLogInfo.append(arrayParam[i]).append("\n");
                }
            }else{
                mLogInfo.append(requestUrl).append("\n");
            }

            if ("POST".equals(request.method())) {

                if (request.body() instanceof FormBody) {
                    mLogInfo.append("请求参数：");
                    FormBody formBody = (FormBody) request.body();
                    for (int i = 0; i < formBody.size(); i++) {
                        String name = formBody.encodedName(i);
                        String value = formBody.value(i);
                        if (i != 0) {
                            mLogInfo.append("　　　　　");
                        }
                        mLogInfo.append(name).append("=").append(value).append("\n");
                    }
                } else if (request.body() instanceof MultipartBody) {
                    mLogInfo.append("请求参数（上传文件操作，暂时不能打印请求参数>_<）：").append("\n");
                } else {
                    Invocation invocation=request.tag(Invocation.class);
                    if(invocation!=null){
                        if(invocation.arguments().size()>1) {
                            mLogInfo.append("Body请求参数：")
                                    .append("\n")
                                    .append(formatJson(new Gson().toJson(invocation.arguments())))
                                    .append("\n");
                        }
                    }
                }
            }

            mLogInfo.append("响应耗时：");
            mLogInfo.append(formatDuring(response.receivedResponseAtMillis() - response.sentRequestAtMillis()));
            mLogInfo.append("\n");

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            mLogInfo.append("响应数据：");
            mLogInfo.append("\n");
            mLogInfo.append(formatJson(responseBody.string()));

            Logger.t(TAG+"-"+tag);
            Logger.d(mLogInfo.toString());
    }

    /**
     * 格式化json字符串
     */
    private String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }
    /**
     * 添加space
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    private static String formatDuring(long ms) {
        if (ms < 1000) {
            return ms + "ms";
        } else if (ms < 1000 * 60) {
            return (ms % (1000 * 60)) / 1000 + "s";
        } else {
            return (ms % (1000 * 60 * 60)) / (1000 * 60) + "min";
        }
    }

}
