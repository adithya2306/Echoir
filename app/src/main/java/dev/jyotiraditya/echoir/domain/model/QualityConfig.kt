package dev.jyotiraditya.echoir.domain.model

import android.media.MediaCodecList
import android.media.MediaCodecList.ALL_CODECS
import android.media.MediaFormat
import android.media.MediaFormat.MIMETYPE_AUDIO_AC4
import android.media.MediaFormat.MIMETYPE_AUDIO_EAC3_JOC
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

    fun isSupported(): Boolean =
        when (this) {
            DolbyAtmosAC3 -> isDecoderPresent(MIMETYPE_AUDIO_EAC3_JOC)
            DolbyAtmosAC4 -> isDecoderPresent(MIMETYPE_AUDIO_AC4)
            else -> true
        }

    private fun isDecoderPresent(mimeType: String): Boolean =
        MediaCodecList(ALL_CODECS)
            .codecInfos
            .any { codecInfo ->
                !codecInfo.isEncoder && codecInfo.supportedTypes.contains(mimeType)
            }

    companion object {
        private const val MIMETYPE_AUDIO_EAC3_JOC = "audio/eac3-joc"
        private const val MIMETYPE_AUDIO_AC4 = "audio/ac4"
    }
}