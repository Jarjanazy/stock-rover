package com.jalil.stockrover.crawler;

import static java.lang.String.format;

public class UrlFactory
{
    private static final String baseUrl = "https://www.macrotrends.net/stocks/charts";

    public static String getGrossMarginUrl(String stockSymbol, String companyName)
    {
        return format("%s/%s/%s/gross-margin", baseUrl, stockSymbol, companyName);
    }

    public static String getOperatingMarginUrl(String stockSymbol, String companyName)
    {
        return format("%s/%s/%s/operating-margin", baseUrl, stockSymbol, companyName);
    }

    public static String getEBITDAMarginUrl(String stockSymbol, String companyName)
    {
        return format("%s/%s/%s/ebitda-margin", baseUrl, stockSymbol, companyName);
    }

    public static String getPreTaxProfitMarginUrl(String stockSymbol, String companyName)
    {
        return format("%s/%s/%s/pre-tax-profit-margin", baseUrl, stockSymbol, companyName);
    }

    public static String getNetMarginUrl(String stockSymbol, String companyName)
    {
        return format("%s/%s/%s/net-profit-margin", baseUrl, stockSymbol, companyName);
    }
}
