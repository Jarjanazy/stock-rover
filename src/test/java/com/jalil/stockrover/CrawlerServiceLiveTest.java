package com.jalil.stockrover;

import com.jalil.stockrover.crawler.incomeStatement.IncomeStatementCrawlerService;
import com.jalil.stockrover.crawler.margins.MarginsCrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

@SpringBootTest
public class CrawlerServiceLiveTest
{
    @Autowired
    private MarginsCrawlerService marginsCrawlerService;

    @Autowired
    private IncomeStatementCrawlerService incomeStatementCrawlerService;

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenGrossMarginIsRequested_ThenGetIt() throws IOException
    {
        marginsCrawlerService.crawlGrossMargin("MSFT", "microsoft");
    }

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenNetMarginIsRequested_ThenGetIt() throws IOException
    {
        marginsCrawlerService.crawlNetMargin("MSFT", "microsoft");
    }

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenIncomeStatementIsRequested_ThenGetIt() throws IOException, InterruptedException
    {
        incomeStatementCrawlerService.crawlIncomeStatement("AAPL", "apple");
    }
}
