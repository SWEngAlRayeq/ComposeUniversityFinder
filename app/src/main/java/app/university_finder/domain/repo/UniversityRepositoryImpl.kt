package app.university_finder.domain.repo

import app.university_finder.data.remote.UniversitiesApi
import app.university_finder.domain.model.University
import javax.inject.Inject
import kotlin.collections.map

class UniversityRepositoryImpl @Inject constructor(
    private val api: UniversitiesApi
): UniversityRepository {
    override suspend fun getUniversityByCountry(country: String): List<University> {
        return api.searchByCountry(country).map { dto ->
            University(
                name = dto.name.orEmpty(),
                country = dto.country.orEmpty(),
                website = dto.web_pages?.firstOrNull().orEmpty()
            )
        }
    }
}