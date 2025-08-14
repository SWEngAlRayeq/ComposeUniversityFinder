package app.university_finder.domain.repo

import app.university_finder.domain.model.Country

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}