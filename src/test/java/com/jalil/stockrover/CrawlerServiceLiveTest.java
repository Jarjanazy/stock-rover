package com.jalil.stockrover;

import com.jalil.stockrover.crawler.balancesheet.BalanceSheetCrawlerService;
import com.jalil.stockrover.crawler.cashflowstatement.CashFlowStatementCrawlerService;
import com.jalil.stockrover.crawler.incomeStatement.IncomeStatementCrawlerService;
import com.jalil.stockrover.crawler.margins.MarginsCrawlerService;
import com.jalil.stockrover.crawler.stockscreener.StockScreenerCrawlerService;
import com.jalil.stockrover.domain.company.Company;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

@SpringBootTest
@Disabled
public class CrawlerServiceLiveTest
{
    @Autowired
    private MarginsCrawlerService marginsCrawlerService;

    @Autowired
    private IncomeStatementCrawlerService incomeStatementCrawlerService;

    @Autowired
    private BalanceSheetCrawlerService balanceSheetCrawlerService;

    @Autowired
    private CashFlowStatementCrawlerService cashFlowStatementCrawlerService;

    @Autowired
    private StockScreenerCrawlerService stockScreenerCrawlerService;

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenGrossMarginIsRequested_ThenGetIt() throws IOException
    {
        Company company = Company.builder().companyName("microsoft").companySymbol("MSFT").build();
        marginsCrawlerService.crawlGrossMargin(company);
    }

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenNetMarginIsRequested_ThenGetIt() throws IOException
    {
        Company company = Company.builder().companyName("microsoft").companySymbol("MSFT").build();

        marginsCrawlerService.crawlNetMargin(company);
    }

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenIncomeStatementIsRequested_ThenGetIt() throws IOException, InterruptedException
    {
        Company company = Company.builder().companyName("apple").companySymbol("AAPL").build();

        incomeStatementCrawlerService.crawlIncomeStatement(company);
    }

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenBalanceSheetIsRequested_ThenGetIt() throws IOException
    {
        Company company = Company.builder().companyName("apple").companySymbol("AAPL").build();

        balanceSheetCrawlerService.crawlBalanceSheet(company);
    }

    @Test
    public void givenTheAppleStockPageOnMacroTrends_WhenCashFlowStatementIsRequested_ThenGetIt() throws IOException
    {
        Company company = Company.builder().companyName("apple").companySymbol("AAPL").build();

        cashFlowStatementCrawlerService.crawlCashFlowStatement(company);
    }

    @Test
    public void givenStockScreenerPageOnMacroTrends_WhenStocksAreRequested_ThenFetchThem() throws IOException
    {
        stockScreenerCrawlerService.crawlStockScreener();
    }
}
