package com.jalil.stockrover.crawler.incomestatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.incomeStatement.IncomeStatementCrawlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URL;

import static com.jalil.stockrover.crawler.WebClientFactory.createWebClient;
import static com.jalil.stockrover.crawler.WebClientFactory.createWebClientForTableRetrieval;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class IncomeStatementCrawlerServiceTest
{
    private IncomeStatementCrawlerService incomeStatementCrawlerService;

    @Mock
    private HtmlPageFetcher htmlPageFetcher;


    @BeforeEach
    public void setup()
    {
        incomeStatementCrawlerService = new IncomeStatementCrawlerService(htmlPageFetcher);
    }

    @Test
    public void givenIncomeStatementPageUrl_WhenItHas2RowsAnd2Columns_ThenConvertItToIncomeStatement() throws IOException, InterruptedException
    {
        URL input = getClass().getResource("/incomeStatementTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClientForTableRetrieval().getPage(input);

        when(htmlPageFetcher.getIncomeStatementHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        incomeStatementCrawlerService.crawlIncomeStatement("AAPL", "Apple");

    }


}
