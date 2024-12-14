package dev.jyotiraditya.echoir.domain.model

import dev.jyotiraditya.echoir.R

sealed class QualityConfig(
    val label: Int,
    val shortLabel: Int = label,
    val quality: String,
    val ac4: Boolean = false,
    val immersive: Boolean = true,
    val summary: Int
) {
    data object HiRes : QualityConfig(
        label = R.string.hires_label,
        shortLabel = R.string.hires_short,
        quality = "HI_RES_LOSSLESS",
        summary = R.string.hires_summary
    )

    data object Lossless : QualityConfig(
        label = R.string.lossless_label,
        shortLabel = R.string.lossless_short,
        quality = "LOSSLESS",
        summary = R.string.lossless_summary
    )

    data object AAC320 : QualityConfig(
        label = R.string.aac_320_label,
        shortLabel = R.string.aac_320_short,
        quality = "HIGH",
        summary = R.string.aac_320_summary
    )

    data object AAC96 : QualityConfig(
        label = R.string.aac_96_label,
        shortLabel = R.string.aac_96_short,
        quality = "LOW",
        summary = R.string.aac_96_summary
    )

    data object DolbyAtmosAC3 : QualityConfig(
        label = R.string.dolby_ac3_label,
        shortLabel = R.string.dolby_ac3_short,
        quality = "DOLBY_ATMOS",
        summary = R.string.dolby_ac3_summary
    )

    data object DolbyAtmosAC4 : QualityConfig(
        label = R.string.dolby_ac4_label,
        shortLabel = R.string.dolby_ac4_short,
        quality = "DOLBY_ATMOS",
        ac4 = true,
        summary = R.string.dolby_ac4_summary
    )
}