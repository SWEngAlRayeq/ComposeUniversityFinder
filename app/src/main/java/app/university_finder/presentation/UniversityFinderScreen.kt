package app.university_finder.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.university_finder.presentation.viewmodel.UniversityFinderViewModel

@Composable
fun UniversityFinderScreen(viewModel: UniversityFinderViewModel = hiltViewModel()) {

    val countries by viewModel.countries
    val selected by viewModel.selectedCountry
    val universities by viewModel.universities
    val loadingCountries by viewModel.isLoadingCountries
    val loadingUniversities by viewModel.isLoadingUniversity
    val error by viewModel.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Brush.verticalGradient(listOf(Color(0xFF0F1724), Color(0xFF0B3A58))))
            .padding(16.dp)
    ) {
        Text(
            "🎓 University Finder",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        if (loadingCountries) {
            Box(
                Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            CountryFullWidthDropdown(
                countries = countries,
                selected = selected,
                onSelected = { viewModel.onCountrySelected(it) }
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier =
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { viewModel.refresh() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DB954))) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                Spacer(Modifier.width(8.dp))
                Text("Refresh", color = Color.White)
            }
        }

        Spacer(Modifier.height(12.dp))

        when {
            error != null -> Text("❌ $error", color = Color.Red)
            loadingUniversities -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = Color.White)
            }
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(universities){university ->
                    UniversityCard(university)
                }
            }
        }

    }

}

