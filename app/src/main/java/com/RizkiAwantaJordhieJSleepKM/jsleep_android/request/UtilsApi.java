package com.RizkiAwantaJordhieJSleepKM.jsleep_android.request;

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.175.167:25555";

    public static BaseApiService getApiService(){
     return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
