package com.example.weatherandroidtest.services.http

import android.content.Context
import android.util.Log
import com.example.weatherandroidtest.helpers.DateDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HttpClient(context: Context) {

    companion object {
        const val BASE_URL: String = "http://api.openweathermap.org/"
    }

    var context = context;

    fun buildRestRetrofit(): Retrofit {
        val gson: Gson =
            GsonBuilder().registerTypeAdapter(
                Date::class.java,
                DateDeserializer("dd/MM/yyyy HH:mm:ss")
            ).create();

        return Retrofit.Builder().baseUrl("$BASE_URL")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            var okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder();
            okHttpClient.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            okHttpClient.connectTimeout(180, TimeUnit.SECONDS);
            okHttpClient.readTimeout(300, TimeUnit.SECONDS);
            okHttpClient.writeTimeout(300, TimeUnit.SECONDS);
            okHttpClient.interceptors().add(createInterceptor());
            return okHttpClient.build();
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun createInterceptor(): Interceptor {

        return Interceptor { chain ->
            var request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")

            Log.e("createInterceptor","url ${chain.request().url().host()}")
            Log.e("createInterceptor","url ${chain.request().url().toString()}")
            Log.e("createInterceptor","url ${chain.request().url().query()}")
            chain.proceed(request.build())
        }

    }
}