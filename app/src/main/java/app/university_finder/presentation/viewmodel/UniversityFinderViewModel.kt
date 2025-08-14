package app.university_finder.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.university_finder.domain.model.Country
import app.university_finder.domain.model.University
import app.university_finder.domain.usecase.GetCountriesUseCase
import app.university_finder.domain.usecase.GetUniversitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversityFinderViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getUniversitiesUseCase: GetUniversitiesUseCase
) : ViewModel() {

    val countries = mutableStateOf<List<Country>>(emptyList())
    val selectedCountry = mutableStateOf<Country?>(null)
    val universities = mutableStateOf<List<University>>(emptyList())

    val isLoadingCountries = mutableStateOf(true)
    val isLoadingUniversity = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            isLoadingCountries.value = true
            try {
                countries.value = getCountriesUseCase()
                selectedCountry.value = countries.value.firstOrNull()
                selectedCountry.value?.let { fetchUniversities(it) }
            }catch (e: Exception) {
                error.value = e.message ?: "Failed to load countries"
            } finally {
                isLoadingCountries.value = false
            }
        }
    }

    fun onCountrySelected(country: Country) {
        selectedCountry.value = country
        fetchUniversities(country)
    }

    private fun fetchUniversities(country: Country) {
        viewModelScope.launch {
            isLoadingUniversity.value = true
            try {
                universities.value = getUniversitiesUseCase(country.name)
            } catch (e: Exception) {
                error.value = e.message ?: "Failed to load universities"
            } finally {
                isLoadingUniversity.value = false
            }
        }
    }

    fun refresh() {
        selectedCountry.value?.let {
            fetchUniversities(it)
        }
    }


}