package com.jalil.stockrover.common;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.jalil.stockrover.common.UrlFactory.*;
import static com.jalil.stockrover.common.WebClientFactory.createWebClient;
import static com.jalil.stockrover.common.WebClientFactory.createWebClientForTableRetrieval;

@Service
public class HtmlPageFetcher
{
    public HtmlPage getGrossMarginsHtmlPage(String stockSymbol, String companyName) throws IOException
    {
        return createWebClient()
                .getPage(getGrossMarginUrl(stockSymbol, companyName));
    }

    public HtmlPage getNetMarginsHtmlPage(String stockSymbol, String companyName) throws IOException
    {
        return createWebClient()
                .getPage(getNetMarginUrl(stockSymbol, companyName));
    }

    public HtmlPage getIncomeStatementHtmlPage(String stockSymbol, String companyName) throws IOException
    {
        return createWebClientForTableRetrieval()
                .getPage(getIncomeStatementUrl(stockSymbol, companyName));
    }

    public HtmlPage getBalanceSheetHtmlPage(String stockSymbol, String companyName) throws IOException
    {
        return createWebClientForTableRetrieval()
                .getPage(getBalanceSheetUrl(stockSymbol, companyName));
    }

    public HtmlPage getCashFlowStatementHtmlPage(String stockSymbol, String companyName) throws IOException
    {
        return createWebClientForTableRetrieval()
                .getPage(getCashFlowStatementUrl(stockSymbol, companyName));
    }

    public HtmlPage getStockScreenerHtmlPage() throws IOException
    {
        return createWebClientForTableRetrieval().getPage(getStocksScreenerUrl());
    }

}
