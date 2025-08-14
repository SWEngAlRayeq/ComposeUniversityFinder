package app.university_finder.data.model

import com.google.gson.annotations.SerializedName

data class UniversityDto(
    @SerializedName("name") val name: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("web_pages") val web_pages: List<String>?
)