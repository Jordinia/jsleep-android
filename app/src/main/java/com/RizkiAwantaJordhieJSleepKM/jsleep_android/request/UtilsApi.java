package com.RizkiAwantaJordhieJSleepKM.jsleep_android.request;

public class UtilsApi {
    public static final String BASE_URL_API = "http://172.18.13.167:25555";

    public static BaseApiService getApiService(){
     return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
