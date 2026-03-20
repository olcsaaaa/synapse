package com.olidev.synapse_mobile

import com.olidev.synapse_mobile.data.local.SessionManager
import com.olidev.synapse_mobile.data.local.SynapseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://synapse.dev-oli.hu")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSynapseApi(retrofit: Retrofit): SynapseApi = retrofit.create(SynapseApi::class.java)

    @Provides
    @Singleton
    fun providesOkHttpClient(sessionManager: SessionManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = runBlocking { sessionManager.sessionToken.first() }
                val request = chain.request().newBuilder()

                if (token != null) {
                    request.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(request.build())
            }
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://synapse.dev-oli.hu")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}