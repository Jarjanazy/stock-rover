package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.jalil.stockrover.crawler.UrlFactory.getGrossMarginUrl;
import static com.jalil.stockrover.crawler.WebClientFactory.createWebClient;

@Service
public class HtmlPageFetcher
{
    public HtmlPage getGrossMarginsHtmlPage(String stockSymbol, String companyName) throws IOException
    {
        return createWebClient()
                .getPage(getGrossMarginUrl(stockSymbol, companyName));
    }
}
