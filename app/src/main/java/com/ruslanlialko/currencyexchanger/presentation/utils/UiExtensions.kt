package com.ruslanlialko.currencyexchanger.presentation.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun Double.formatAmount(): String {
    val symbols = DecimalFormatSymbols(Locale.US)
    val df = DecimalFormat("0.00", symbols)
    return df.format(this)
}

fun String.toSafeDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}

fun LocalDateTime.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return this.format(formatter)
}

fun String.getFlagEmoji(): String {
    return when (this) {
        "AED" -> "\uD83C\uDDE6\uD83C\uDDEA" // United Arab Emirates
        "AFN" -> "\uD83C\uDDE6\uD83C\uDDEB" // Afghanistan
        "ALL" -> "\uD83C\uDDE6\uD83C\uDDF1" // Albania
        "AMD" -> "\uD83C\uDDE6\uD83C\uDDF2" // Armenia
        "ANG" -> "\uD83C\uDDF3\uD83C\uDDF1" // Netherlands Antilles
        "AOA" -> "\uD83C\uDDE6\uD83C\uDDF4" // Angola
        "ARS" -> "\uD83C\uDDE6\uD83C\uDDF7" // Argentina
        "AUD" -> "\uD83C\uDDE6\uD83C\uDDFA" // Australia
        "AWG" -> "\uD83C\uDDE6\uD83C\uDDFC" // Aruba
        "AZN" -> "\uD83C\uDDE6\uD83C\uDDFF" // Azerbaijan
        "BAM" -> "\uD83C\uDDE7\uD83C\uDDE6" // Bosnia and Herzegovina
        "BBD" -> "\uD83C\uDDE7\uD83C\uDDE7" // Barbados
        "BDT" -> "\uD83C\uDDE7\uD83C\uDDE9" // Bangladesh
        "BGN" -> "\uD83C\uDDE7\uD83C\uDDEC" // Bulgaria
        "BHD" -> "\uD83C\uDDE7\uD83C\uDDED" // Bahrain
        "BIF" -> "\uD83C\uDDE7\uD83C\uDDEE" // Burundi
        "BMD" -> "\uD83C\uDDE7\uD83C\uDDF2" // Bermuda
        "BND" -> "\uD83C\uDDE7\uD83C\uDDF3" // Brunei
        "BOB" -> "\uD83C\uDDE7\uD83C\uDDF4" // Bolivia
        "BRL" -> "\uD83C\uDDE7\uD83C\uDDF7" // Brazil
        "BSD" -> "\uD83C\uDDE7\uD83C\uDDF8" // Bahamas
        "BTN" -> "\uD83C\uDDE7\uD83C\uDDF9" // Bhutan
        "BWP" -> "\uD83C\uDDE7\uD83C\uDDFC" // Botswana
        "BYN" -> "\uD83C\uDDE7\uD83C\uDDFE" // Belarus
        "BYR" -> "\uD83C\uDDE7\uD83C\uDDFE" // Belarus (old code)
        "BZD" -> "\uD83C\uDDE7\uD83C\uDDFF" // Belize
        "CAD" -> "\uD83C\uDDE8\uD83C\uDDE6" // Canada
        "CDF" -> "\uD83C\uDDE8\uD83C\uDDE9" // Congo-Kinshasa
        "CHF" -> "\uD83C\uDDE8\uD83C\uDDED" // Switzerland
        "CLF" -> "\uD83C\uDDE8\uD83C\uDDF1" // Chile (Unidad de Fomento)
        "CLP" -> "\uD83C\uDDE8\uD83C\uDDF1" // Chile
        "CNY" -> "\uD83C\uDDE8\uD83C\uDDF3" // China
        "COP" -> "\uD83C\uDDE8\uD83C\uDDF4" // Colombia
        "CRC" -> "\uD83C\uDDE8\uD83C\uDDF7" // Costa Rica
        "CUC" -> "\uD83C\uDDE8\uD83C\uDDFA" // Cuba (convertible peso)
        "CUP" -> "\uD83C\uDDE8\uD83C\uDDFA" // Cuba
        "CVE" -> "\uD83C\uDDE8\uD83C\uDDFB" // Cape Verde
        "CZK" -> "\uD83C\uDDE8\uD83C\uDDFF" // Czech Republic
        "DJF" -> "\uD83C\uDDE9\uD83C\uDDEF" // Djibouti
        "DKK" -> "\uD83C\uDDE9\uD83C\uDDF0" // Denmark
        "DOP" -> "\uD83C\uDDE9\uD83C\uDDF4" // Dominican Republic
        "DZD" -> "\uD83C\uDDE9\uD83C\uDDFF" // Algeria
        "EGP" -> "\uD83C\uDDEA\uD83C\uDDEC" // Egypt
        "ERN" -> "\uD83C\uDDEA\uD83C\uDDF7" // Eritrea
        "ETB" -> "\uD83C\uDDEA\uD83C\uDDF9" // Ethiopia
        "EUR" -> "\uD83C\uDDEA\uD83C\uDDFA" // European Union
        "FJD" -> "\uD83C\uDDEB\uD83C\uDDEF" // Fiji
        "FKP" -> "\uD83C\uDDEB\uD83C\uDDF0" // Falkland Islands
        "GBP" -> "\uD83C\uDDEC\uD83C\uDDE7" // United Kingdom
        "GEL" -> "\uD83C\uDDEC\uD83C\uDDEA" // Georgia
        "GGP" -> "\uD83C\uDDEC\uD83C\uDDEC" // Guernsey
        "GHS" -> "\uD83C\uDDEC\uD83C\uDDED" // Ghana
        "GIP" -> "\uD83C\uDDEC\uD83C\uDDEE" // Gibraltar
        "GMD" -> "\uD83C\uDDEC\uD83C\uDDF2" // Gambia
        "GNF" -> "\uD83C\uDDEC\uD83C\uDDF3" // Guinea
        "GTQ" -> "\uD83C\uDDEC\uD83C\uDDF9" // Guatemala
        "GYD" -> "\uD83C\uDDEC\uD83C\uDDFE" // Guyana
        "HKD" -> "\uD83C\uDDED\uD83C\uDDF0" // Hong Kong
        "HNL" -> "\uD83C\uDDED\uD83C\uDDF3" // Honduras
        "HRK" -> "\uD83C\uDDED\uD83C\uDDF7" // Croatia
        "HTG" -> "\uD83C\uDDED\uD83C\uDDF9" // Haiti
        "HUF" -> "\uD83C\uDDED\uD83C\uDDFA" // Hungary
        "USD" -> "\uD83C\uDDFA\uD83C\uDDF8" // United States
        "JPY" -> "\uD83C\uDDEF\uD83C\uDDF5" // Japan
        "SEK" -> "\uD83C\uDDF8\uD83C\uDDEA" // Sweden
        "NZD" -> "\uD83C\uDDF3\uD83C\uDDFF" // New Zealand
        "MXN" -> "\uD83C\uDDF2\uD83C\uDDFD" // Mexico
        "SGD" -> "\uD83C\uDDF8\uD83C\uDDEC" // Singapore
        "NOK" -> "\uD83C\uDDF3\uD83C\uDDF4" // Norway
        "KRW" -> "\uD83C\uDDF0\uD83C\uDDF7" // South Korea
        "TRY" -> "\uD83C\uDDF9\uD83C\uDDF7" // Turkey
        "RUB" -> "\uD83C\uDDF7\uD83C\uDDFA" // Russia
        "INR" -> "\uD83C\uDDEE\uD83C\uDDF3" // India
        "ZAR" -> "\uD83C\uDDFF\uD83C\uDDE6" // South Africa
        "PHP" -> "\uD83C\uDDF5\uD83C\uDDED" // Philippines
        "IDR" -> "\uD83C\uDDEE\uD83C\uDDE9" // Indonesia
        "THB" -> "\uD83C\uDDF9\uD83C\uDDED" // Thailand
        "MYR" -> "\uD83C\uDDF2\uD83C\uDDFE" // Malaysia
        "PLN" -> "\uD83C\uDDF5\uD83C\uDDF1" // Poland
        "UAH" -> "\uD83C\uDDFA\uD83C\uDDE6" // Ukraine
        "VND" -> "\uD83C\uDDFB\uD83C\uDDF3" // Vietnam
        "SAR" -> "\uD83C\uDDF8\uD83C\uDDE6" // Saudi Arabia
        "KWD" -> "\uD83C\uDDF0\uD83C\uDDFC" // Kuwait
        "QAR" -> "\uD83C\uDDF6\uD83C\uDDE6" // Qatar
        "PEN" -> "\uD83C\uDDF5\uD83C\uDDEA" // Peru
        "UYU" -> "\uD83C\uDDFA\uD83C\uDDFE" // Uruguay
        "PYG" -> "\uD83C\uDDF5\uD83C\uDDFE" // Paraguay
        "KZT" -> "\uD83C\uDDF0\uD83C\uDDFF" // Kazakhstan
        "TJS" -> "\uD83C\uDDF9\uD83C\uDDEF" // Tajikistan
        "TMT" -> "\uD83C\uDDF9\uD83C\uDDF2" // Turkmenistan
        "MDL" -> "\uD83C\uDDF2\uD83C\uDDE9" // Moldova
        "KGS" -> "\uD83C\uDDF0\uD83C\uDDEC" // Kyrgyzstan
        "UZS" -> "\uD83C\uDDFA\uD83C\uDDFF" // Uzbekistan
        "ILS" -> "\uD83C\uDDF8\uD83C\uDDEE" // Israel
        "IMP" -> "\uD83C\uDDEE\uD83C\uDDF2" // Isle of Man
        "IQD" -> "\uD83C\uDDEE\uD83C\uDDF6" // Iraq
        "IRR" -> "\uD83C\uDDEE\uD83C\uDDF7" // Iran
        "ISK" -> "\uD83C\uDDEE\uD83C\uDDF8" // Iceland
        "JEP" -> "\uD83C\uDDEF\uD83C\uDDEA" // Jersey
        "JMD" -> "\uD83C\uDDEF\uD83C\uDDF2" // Jamaica
        "JOD" -> "\uD83C\uDDEF\uD83C\uDDF4" // Jordan
        "KES" -> "\uD83C\uDDF0\uD83C\uDDEA" // Kenya
        "KHR" -> "\uD83C\uDDF0\uD83C\uDDED" // Cambodia
        "KMF" -> "\uD83C\uDDF0\uD83C\uDDF2" // Comoros
        "KPW" -> "\uD83C\uDDF0\uD83C\uDDF5" // North Korea
        "KYD" -> "\uD83C\uDDF0\uD83C\uDDFE" // Cayman Islands
        "LAK" -> "\uD83C\uDDF1\uD83C\uDDE6" // Laos
        "LBP" -> "\uD83C\uDDF1\uD83C\uDDE7" // Lebanon
        "LKR" -> "\uD83C\uDDF1\uD83C\uDDF0" // Sri Lanka
        "LRD" -> "\uD83C\uDDF1\uD83C\uDDF7" // Liberia
        "LSL" -> "\uD83C\uDDF1\uD83C\uDDF8" // Lesotho
        "LTL" -> "\uD83C\uDDF1\uD83C\uDDF9" // Lithuania
        "LVL" -> "\uD83C\uDDF1\uD83C\uDDFB" // Latvia
        "LYD" -> "\uD83C\uDDF1\uD83C\uDDFE" // Libya
        "MAD" -> "\uD83C\uDDF2\uD83C\uDDE6" // Morocco
        "MGA" -> "\uD83C\uDDF2\uD83C\uDDEC" // Madagascar
        "MKD" -> "\uD83C\uDDF2\uD83C\uDDF0" // North Macedonia
        "MMK" -> "\uD83C\uDDF2\uD83C\uDDF2" // Myanmar
        "MNT" -> "\uD83C\uDDF2\uD83C\uDDF3" // Mongolia
        "MOP" -> "\uD83C\uDDF2\uD83C\uDDF4" // Macau
        "MRO" -> "\uD83C\uDDF2\uD83C\uDDF7" // Mauritania
        "MUR" -> "\uD83C\uDDF2\uD83C\uDDF7" // Mauritius
        "MVR" -> "\uD83C\uDDF2\uD83C\uDDFB" // Maldives
        "MWK" -> "\uD83C\uDDF2\uD83C\uDDFC" // Malawi
        "MZN" -> "\uD83C\uDDF2\uD83C\uDDFF" // Mozambique
        "NAD" -> "\uD83C\uDDF3\uD83C\uDDE6" // Namibia
        "NGN" -> "\uD83C\uDDF3\uD83C\uDDEC" // Nigeria
        "NIO" -> "\uD83C\uDDF3\uD83C\uDDEE" // Nicaragua
        "NPR" -> "\uD83C\uDDF3\uD83C\uDDF5" // Nepal
        "OMR" -> "\uD83C\uDDF4\uD83C\uDDF2" // Oman
        "PAB" -> "\uD83C\uDDF5\uD83C\uDDE6" // Panama
        "PKR" -> "\uD83C\uDDF5\uD83C\uDDF0" // Pakistan
        "RON" -> "\uD83C\uDDF7\uD83C\uDDF4" // Romania
        "RSD" -> "\uD83C\uDDF7\uD83C\uDDF8" // Serbia
        "RWF" -> "\uD83C\uDDF7\uD83C\uDDFC" // Rwanda
        "SBD" -> "\uD83C\uDDF8\uD83C\uDDE7" // Solomon Islands
        "SCR" -> "\uD83C\uDDF8\uD83C\uDDF1" // Seychelles
        "SDG" -> "\uD83C\uDDF8\uD83C\uDDF3" // Sudan
        "SHP" -> "\uD83C\uDDF8\uD83C\uDDED" // Saint Helena
        "SLL" -> "\uD83C\uDDF8\uD83C\uDDF1" // Sierra Leone
        "SOS" -> "\uD83C\uDDF8\uD83C\uDDFF" // Somalia
        "SRD" -> "\uD83C\uDDF8\uD83C\uDDF7" // Suriname
        "STD" -> "\uD83C\uDDF8\uD83C\uDDF6" // Sao Tome and Principe
        "SVC" -> "\uD83C\uDDE8\uD83C\uDDE8" // El Salvador
        "SYP" -> "\uD83C\uDDF8\uD83C\uDDF4" // Syria
        "SZL" -> "\uD83C\uDDF8\uD83C\uDDF3" // Eswatini
        "TND" -> "\uD83C\uDDF8\uD83C\uDDF3" // Tunisia
        "TOP" -> "\uD83C\uDDF8\uD83C\uDDF6" // Tonga
        "TTD" -> "\uD83C\uDDF8\uD83C\uDDF1" // Trinidad and Tobago
        "TWD" -> "\uD83C\uDDF8\uD83C\uDDF8" // Taiwan
        "TZS" -> "\uD83C\uDDF8\uD83C\uDDE6" // Tanzania
        "UGX" -> "\uD83C\uDDFA\uD83C\uDDEC" // Uganda
        "VEF" -> "\uD83C\uDDEA\uD83C\uDDF8" // Venezuela
        "VUV" -> "\uD83C\uDDEC\uD83C\uDDFB" // Vanuatu
        "XAF" -> "\uD83C\uDDE8\uD83C\uDDF2" // Central African CFA Franc (used by several countries)
        "XAG" -> "\uD83C\uDDE6\uD83C\uDDF7" // Generic symbol for Silver (XAG is the code for silver)
        "XAU" -> "\uD83C\uDDE8\uD83C\uDDF6" // Generic symbol for Gold (XAU is the code for gold)
        "XCD" -> "\uD83C\uDDE7\uD83C\uDDF4" // Eastern Caribbean Dollar
        "XDR" -> "\uD83C\uDDE7\uD83C\uDDEF" // Special Drawing Rights (SDR)
        "XOF" -> "\uD83C\uDDE8\uD83C\uDDFC" // West African CFA Franc (used by several countries)
        "XPF" -> "\uD83C\uDDE8\uD83C\uDDFC" // CFP Franc (used by several countries)
        "YER" -> "\uD83C\uDDFE\uD83C\uDDED" // Yemen
        "ZMK" -> "\uD83C\uDDFF\uD83C\uDDF2" // Zambia (former code before ZMW)
        "ZMW" -> "\uD83C\uDDFF\uD83C\uDDEC" // Zambia
        "ZWL" -> "\uD83C\uDDFF\uD83C\uDDF0" // Zimbabwe
        else -> "\uD83C\uDFF3" // Default flag
    }
}