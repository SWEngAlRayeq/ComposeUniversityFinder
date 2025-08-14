package app.university_finder.data.remote

import app.university_finder.data.model.UniversityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversitiesApi {

    @GET("search")
    suspend fun searchByCountry(@Query("country") country: String):
            List<UniversityDto>

}