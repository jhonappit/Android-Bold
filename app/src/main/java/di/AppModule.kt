package di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jhonjto.android_bold.BuildConfig
import com.jhonjto.android_bold.data.ForecastRepositoryImpl
import com.jhonjto.android_bold.data.SearchRepositoryImpl
import com.jhonjto.android_bold.data.WeatherApi
import com.jhonjto.android_bold.domain.ForecastRepository
import com.jhonjto.android_bold.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import utils.ApiKeyInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return BuildConfig.API_URL
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                provideAuthInterceptor()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor(BuildConfig.API_KEY)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideSearchRepository(weatherApi: WeatherApi): SearchRepository {
        return SearchRepositoryImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun provideForecastRepository(weatherApi: WeatherApi): ForecastRepository {
        return ForecastRepositoryImpl(weatherApi)
    }
}
