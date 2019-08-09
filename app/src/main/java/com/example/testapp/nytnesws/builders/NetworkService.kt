package com.example.testapp.nytnesws.builders

import com.example.testapp.nytnesws.api.NYTClient
import com.example.testapp.nytnesws.models.Result
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.net.ssl.*
import java.security.cert.CertificateException

class NetworkService private constructor() {
    private val retrofit: Retrofit

    val jsonApi: NYTClient
        get() = retrofit.create(NYTClient::class.java)

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        val gson = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Result::class.java, MyDeserializer())
            .create()



        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            // .addConverterFactory(JacksonConverterFactory.create())
            .client(unsafeOkHttpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //.client(client.build())
            .build()
    }

    companion object {

        private lateinit var mInstance: NetworkService
        const val BASE_URL = "https://api.nytimes.com"

        val instance: NetworkService
            get() {
                mInstance = NetworkService()
                return mInstance
            }

        // Create a trust manager that does not validate certificate chains
        // Install the all-trusting trust manager
        // Create an ssl socket factory with our all-trusting manager
        val unsafeOkHttpClient: OkHttpClient.Builder
            get() {

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

                    val builder = OkHttpClient.Builder()
                    builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    builder.hostnameVerifier { hostname, session -> true }
                    return builder
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }

            }
    }
}
