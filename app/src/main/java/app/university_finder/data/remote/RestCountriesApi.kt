package app.university_finder.data.remote

import app.university_finder.data.model.RestCountryDto
import retrofit2.http.GET

interface RestCountriesApi {

    @GET("all?fields=name,flags,cca2")
    suspend fun getAllCountries(): List<RestCountryDto>

}