package dev.jyotiraditya.echoir.domain.model

import android.content.Context
import dev.jyotiraditya.echoir.R

enum class Region(val code: String) {
    ALBANIA("AL"),
    ARGENTINA("AR"),
    AUSTRALIA("AU"),
    AUSTRIA("AT"),
    BELGIUM("BE"),
    BRAZIL("BR"),
    CANADA("CA"),
    CHILE("CL"),
    COLOMBIA("CO"),
    DOMINICAN_REPUBLIC("DO"),
    FRANCE("FR"),
    GERMANY("DE"),
    HONG_KONG("HK"),
    ISRAEL("IL"),
    ITALY("IT"),
    JAMAICA("JM"),
    MALAYSIA("MY"),
    MEXICO("MX"),
    NEW_ZEALAND("NZ"),
    NIGERIA("NG"),
    PERU("PE"),
    PUERTO_RICO("PR"),
    SINGAPORE("SG"),
    SOUTH_AFRICA("ZA"),
    SPAIN("ES"),
    THAILAND("TH"),
    UGANDA("UG"),
    UNITED_ARAB_EMIRATES("AE"),
    UNITED_KINGDOM("GB"),
    UNITED_STATES("US");

    fun getDisplayName(context: Context): String =
        context.resources.getStringArray(R.array.country_names)[ordinal]

    companion object {
        fun fromCode(code: String): Region = entries.find { it.code == code } ?: BRAZIL
    }
}