package app.university_finder.data.repo_impl

import app.university_finder.data.remote.RestCountriesApi
import app.university_finder.domain.model.Country
import app.university_finder.domain.repo.CountryRepository
import javax.inject.Inject
import kotlin.collections.mapNotNull

class CountryRepositoryImpl @Inject constructor(
    private val api: RestCountriesApi
):  CountryRepository {
    override suspend fun getCountries(): List<Country> {
        return api.getAllCountries()
            .mapNotNull { dto ->
                val name = dto.name?.common ?: return@mapNotNull null
                val code = dto.cca2
                val flag = dto.flags?.png ?: code?.let { "https://flagcdn.com/w80/${it.lowercase()}.png" } ?: ""
                Country(name = name, code = code, flagUrl = flag)
            }
            .sortedBy { it.name }
    }
}