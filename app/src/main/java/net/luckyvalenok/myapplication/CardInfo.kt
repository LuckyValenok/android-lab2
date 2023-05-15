package net.luckyvalenok.myapplication

import com.google.gson.annotations.SerializedName

data class CardInfo(
    @SerializedName("title") val title: String = "",
    @SerializedName("subtitle") val subtitle: String = "",
    @SerializedName("img") val image: String? = null,
    @SerializedName("hasBag") val background: String? = null,
    @SerializedName("isCircle") val circle: Boolean? = null
)

sealed class CardType(open val title: String, open val subtitle: String) {
    data class WithTitleAndSubtitle(override val title: String, override val subtitle: String) :
        CardType(title, subtitle)

    data class WithImageAndBackground(
        override val title: String,
        override val subtitle: String,
        val image: String,
        val background: String
    ) : CardType(title, subtitle)

    data class WithImage(override val title: String, override val subtitle: String, val image: String) :
        CardType(title, subtitle)

    data class WithImageAndCircle(
        override val title: String,
        override val subtitle: String,
        val image: String,
        val circle: Boolean
    ) : CardType(title, subtitle)

    companion object {
        private fun getFromRequest(request: CardInfo): CardType {
            return if (request.image == null) {
                WithTitleAndSubtitle(request.title, request.subtitle)
            } else if (request.circle != null) {
                WithImageAndCircle(request.title, request.subtitle, request.image, request.circle)
            } else if (request.background != null) {
                WithImageAndBackground(request.title, request.subtitle, request.image, request.background)
            } else {
                WithImage(request.title, request.subtitle, request.image)
            }
        }

        fun getFromListRequest(request: List<CardInfo>): List<CardType> {
            return request.map { getFromRequest(it) }
        }
    }
}

