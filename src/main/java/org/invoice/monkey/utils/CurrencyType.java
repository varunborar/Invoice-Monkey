package org.invoice.monkey.utils;

import java.util.Currency;


public enum CurrencyType {

    NONE,
    UNITED_ARAB_EMIRATES("AED"),
    AFGHANISTAN("AFN"),
    ALBANIA("ALL"),
    ARMENIA("AMD"),
    CURACAO("ANG"),
    ANGOLA("AOA"),
    ARGENTINA("ARS"),
    AUSTRALIA("AUD"),
    ARUBA("AWG"),
    AZERBAIJAN("AZN"),
    BOSNIA_AND_HERZEGOVINA("BAM"),
    BARBADOS("BBD"),
    BANGLADESH("BDT"),
    BULGARIA("BGN"),
    BAHRAIN("BHD"),
    BURUNDI("BIF"),
    BERMUDA("BMD"),
    BRUNEI("BND"),
    BOLIVIA("BOB"),
    BRAZIL("BRL"),
    BAHAMAS("BSD"),
    BHUTAN("BTN"),
    BOTSWANA("BWP"),
    BELARUS("BYN"),
    BELIZE("BZD"),
    CANADA("CAD"),
    SWITZERLAND("CHF"),
    CHILE("CLF"),
    CHINA("CNY"),
    COLOMBIA("COP"),
    COSTA_RICA("CRC"),
    CUBA("CUP"),
    CABO_VERDE("CVE"),
    CZECHIA("CZK"),
    DJIBOUTI("DJF"),
    DENMARK("DKK"),
    DOMINICAN_REPUBLIC("DOP"),
    ALGERIA("DZD"),
    EGYPT("EGP"),
    ERITREA("ERN"),
    ETHIOPIA("ETB"),
    EUROPE("EUR"),
    FIJI("FJD"),
    FALKLAND_ISLAND("FKP"),
    UK("GBP"),
    GEORGIA("GEL"),
    GHANA("GHS"),
    GIBRALTAR("GIP"),
    GAMBIA("GMD"),
    GUINEA("GNF"),
    GUATEMALA("GTQ"),
    GUYANA("GYD"),
    HONG_KONG("HKD"),
    HONDURAS("HNL"),
    CROATIA("HRK"),
    HAITI("HTG"),
    HUNGARY("HUF"),
    INDONESIA("IDR"),
    ISRAEL("ILS"),
    INDIA("INR"),
    IRAQ("IQD"),
    IRAN("IRR"),
    ICELAND("ISK"),
    JAMAICA("JMD"),
    JORDAN("JOD"),
    JAPAN("JPY"),
    KENYA("KES"),
    KYRGYZSTAN("KGS"),
    CAMBODIA("KHR"),
    COMOROS("KMF"),
    NORTH_KOREA("KPW"),
    SOUTH_KOREA("KRW"),
    KUWAIT("KWD"),
    CAYMAN_ISLAND("KYD"),
    KAZAKHSTAN("KZT"),
    LAOS("LAK"),
    LEBANON("LBP"),
    SRI_LANKA("LKR"),
    LIBERIA("LRD"),
    LESOTHO("LSL"),
    LIBYA("LYD"),
    MOROCCO("MAD"),
    MOLDOVA("MDL"),
    MADAGASCAR("MGA"),
    NORTH_MACEDONIA("MKD"),
    MYANMAR("MMK"),
    MONGOLIA("MNT"),
    MACAU("MOP"),
    MAURITIUS("MUR"),
    MALDIVES("MVR"),
    MALAWI("MWK"),
    MEXICO("MXV"),
    MALAYSIA("MYR"),
    MOZAMBIQUE("MZN"),
    NAMIBIA("NAD"),
    NIGERIA("NGN"),
    NICARAGUA("NIO"),
    NORWAY("NOK"),
    NEPAL("NPR"),
    NEW_ZEALAND("NZD"),
    OMAN("OMR"),
    PANAMA("PAB"),
    PERU("PEN"),
    PAPUA_NEW_GUINEA("PGK"),
    PHILIPPINES("PHP"),
    PAKISTAN("PKR"),
    POLAND("PLN"),
    PARAGUAY("PYG"),
    QATAR("QAR"),
    ROMANIA("RON"),
    SERBIA("RSD"),
    RUSSIA("RUB"),
    RWANDA("RWF"),
    SAUDI_ARABIA("SAR"),
    SOLOMON_ISLANDS("SBD"),
    SEYCHELLES("SCR"),
    SUDAN("SDG"),
    SWEDEN("SEK"),
    SINGAPORE("SGD"),
    SAINT_HELENA("SHP"),
    SIERRA_LEONE("SLL"),
    SOMALIA("SOS"),
    SURINAME("SRD"),
    SOUTH_SUDAN("SSP"),
    SAO_TOME_AND_PRINCIPE("STN"),
    EL_SALVADOR("SVC"),
    SYRIA("SYP"),
    ESWATINI("SZL"),
    THAILAND("THB"),
    TAJIKISTAN("TJS"),
    TURKMENISTAN("TMT"),
    TUNISIA("TND"),
    TONGA("TOP"),
    TURKEY("TRY"),
    TRINIDAD_AND_TOBAGO("TTD"),
    TAIWAN("TWD"),
    TANZANIA("TZS"),
    UKRAINE("UAH"),
    UGANDA("UGX"),
    US("USD"),
    URUGUAY("UYU"),
    UZBEKISTAN("UZS"),
    VENEZUELA("VES"),
    VIETNAM("VND"),
    VANUATU("VUV"),
    SAMOA("WST"),
    CAMEROON("XAF"),
    YEMEN("YER"),
    ZAMBIA("ZMW"),
    ZIMBABWE("ZWL");



    private final String Symbol;

    CurrencyType(String country)
    {
        this.Symbol = Currency.getInstance(country).getSymbol();
    }

    CurrencyType()
    {
        this.Symbol = "";
    }

    public String getCurrencySymbol()
    {
        return this.Symbol;
    }

    public static void main(String[] args)
    {
        CurrencyType[] currencyType = CurrencyType.values();
        for(CurrencyType c: currencyType)
            System.out.print(c.toString().replaceAll("_", " ") + ": " +c.getCurrencySymbol() + " ,\n");
    }
}
