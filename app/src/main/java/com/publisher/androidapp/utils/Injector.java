package com.publisher.androidapp.utils;

import android.util.Log;

import com.publisher.androidapp.BuildConfig;
import com.publisher.androidapp.MyApp;
import com.publisher.androidapp.model.Relawan;
import com.publisher.androidapp.service.BeritaService;
import com.publisher.androidapp.service.ForumService;
import com.publisher.androidapp.service.LokerService;
import com.publisher.androidapp.service.PelatihanService;
import com.publisher.androidapp.service.RelawanService;
import com.publisher.androidapp.service.TunaKaryaService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by Febrian Reza on 11-Jan-18.
 */

public class Injector {
    private static final String CACHE_CONTROL = "Cache-Control";

    public static Retrofit provideRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                //.addInterceptor(provideOfflineCacheInterceptor())
                //.addNetworkInterceptor(provideCacheInterceptor())
                //.cache(provideCache())
                .build();
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(MyApp.getInstance().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 100 MB
        } catch (Exception e) {
            Log.e("INJECTOR", "Could not create Cache!");
        }
        return cache;
    }

    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        //Timber.d( message );
                    }
                });
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HEADERS : NONE);
        return httpLoggingInterceptor;
    }

    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!MyApp.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }


    public static BeritaService beritaService() {
        return provideRetrofit(Constant.SERVER_URL).create(BeritaService.class);
    }

    public static LokerService lokerService() {
        return provideRetrofit(Constant.SERVER_URL).create(LokerService.class);
    }

    public static PelatihanService pelatihanService() {
        return provideRetrofit(Constant.SERVER_URL).create(PelatihanService.class);
    }

    public static TunaKaryaService tunaKaryaService(){
        return provideRetrofit(Constant.SERVER_URL).create(TunaKaryaService.class);
    }

    public static RelawanService relawanService(){
        return provideRetrofit(Constant.SERVER_URL).create(RelawanService.class);
    }

    public static ForumService forumService(){
        return  provideRetrofit(Constant.SERVER_URL).create(ForumService.class);
    }
}