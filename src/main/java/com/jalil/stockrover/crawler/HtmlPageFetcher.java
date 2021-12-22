package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.jalil.stockrover.crawler.UrlFactory.*;
import static com.jalil.stockrover.crawler.WebClientFactory.createWebClient;
import static com.jalil.stockrover.crawler.WebClientFactory.createWebClientForTableRetrieval;

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

}
