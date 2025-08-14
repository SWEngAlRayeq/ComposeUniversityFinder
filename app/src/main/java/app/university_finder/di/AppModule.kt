package app.university_finder.di

import app.university_finder.data.remote.RestCountriesApi
import app.university_finder.data.remote.UniversitiesApi
import app.university_finder.data.repo_impl.CountryRepositoryImpl
import app.university_finder.domain.repo.CountryRepository
import app.university_finder.domain.repo.UniversityRepository
import app.university_finder.domain.repo.UniversityRepositoryImpl
import app.university_finder.domain.usecase.GetCountriesUseCase
import app.university_finder.domain.usecase.GetUniversitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("restBase") fun provideRestBase() = "https://restcountries.com/v3.1/"
    @Provides
    @Named("uniBase") fun provideUniBase() = "http://universities.hipolabs.com/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    @Provides
    @Singleton
    fun provideRestCountriesApi(@Named("restBase") baseUrl: String, client: OkHttpClient): RestCountriesApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestCountriesApi::class.java)

    @Provides
    @Singleton
    fun provideUniversitiesApi(@Named("uniBase") baseUrl: String, client: OkHttpClient): UniversitiesApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversitiesApi::class.java)

    @Provides
    @Singleton
    fun provideCountryRepository(api: RestCountriesApi): CountryRepository =
        CountryRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideUniversityRepository(api: UniversitiesApi): UniversityRepository =
        UniversityRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(repo: CountryRepository) = GetCountriesUseCase(repo)

    @Provides
    @Singleton
    fun provideGetUniversitiesUseCase(repo: UniversityRepository) = GetUniversitiesUseCase(repo)
}