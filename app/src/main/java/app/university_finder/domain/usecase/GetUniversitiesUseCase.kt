package app.university_finder.domain.usecase

import app.university_finder.domain.repo.UniversityRepository
import javax.inject.Inject

class GetUniversitiesUseCase @Inject constructor(
    private val repository: UniversityRepository
) {
    suspend operator fun invoke(country: String) =
        repository.getUniversityByCountry(country)
}