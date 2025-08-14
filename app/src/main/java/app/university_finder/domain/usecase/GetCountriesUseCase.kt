package app.university_finder.domain.usecase

import app.university_finder.domain.repo.CountryRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke() = repository.getCountries()
}