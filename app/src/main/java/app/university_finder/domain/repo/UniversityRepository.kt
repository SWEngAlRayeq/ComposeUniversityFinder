package app.university_finder.domain.repo

import app.university_finder.domain.model.University

interface UniversityRepository {
    suspend fun getUniversityByCountry(country: String): List<University>
}