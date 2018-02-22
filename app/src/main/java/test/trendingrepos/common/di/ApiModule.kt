package test.trendingrepos.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import test.trendingrepos.BuildConfig
import test.trendingrepos.common.api.GithubApi
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.common.api.converters.RetrofitEnumConverter
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created on 22/02/2018
 * @author sdelaysam
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun providesHttpLogger(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = when (BuildConfig.HTTP_LOGGER_LEVEL) {
            "body" -> HttpLoggingInterceptor.Level.BODY
            "headers" -> HttpLoggingInterceptor.Level.HEADERS
            "basic" -> HttpLoggingInterceptor.Level.BASIC
            else -> HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun providesGson() = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
                .baseUrl(BuildConfig.GITHUB_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(RetrofitEnumConverter())
                .client(okHttpClient)
                .build()
    @Provides
    @Singleton
    fun providesGithubApi(retrofit: Retrofit) = retrofit.create(GithubApi::class.java)


}