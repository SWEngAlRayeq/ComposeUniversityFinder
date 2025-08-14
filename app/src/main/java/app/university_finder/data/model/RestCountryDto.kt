package app.university_finder.data.model

import com.google.gson.annotations.SerializedName

data class RestCountryDto(
    @SerializedName("cca2") val cca2: String?,
    @SerializedName("name") val name: NameDto?,
    @SerializedName("flags") val flags: FlagsDto?
) {
    data class NameDto(@SerializedName("common") val common: String?)
    data class FlagsDto(@SerializedName("png") val png: String?)
}