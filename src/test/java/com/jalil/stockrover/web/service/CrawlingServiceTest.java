package com.jalil.stockrover.web.service;

import com.jalil.stockrover.crawler.balancesheet.BalanceSheetCrawlerService;
import com.jalil.stockrover.crawler.cashflowstatement.CashFlowStatementCrawlerService;
import com.jalil.stockrover.crawler.incomeStatement.IncomeStatementCrawlerService;
import com.jalil.stockrover.crawler.ratio.RatiosCrawlerService;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrawlingServiceTest
{
    private CrawlingService crawlingService;

    @Mock
    RatiosCrawlerService ratiosCrawlerService;

    @Mock
    BalanceSheetCrawlerService balanceSheetCrawlerService;

    @Mock
    IncomeStatementCrawlerService incomeStatementCrawlerService;

    @Mock
    CashFlowStatementCrawlerService cashFlowStatementCrawlerService;

    @Mock
    ICompanyRepo companyRepo;

    @BeforeEach
    public void setup()
    {
        this.crawlingService = new CrawlingService(companyRepo, ratiosCrawlerService, balanceSheetCrawlerService, incomeStatementCrawlerService, cashFlowStatementCrawlerService);
    }

    @Test
    public void givenCompanyTickerSymbol_WhenFound_ThenCrawlAllData() throws IOException
    {
        List<String> companiesSymbol = singletonList("AAPL");

        Company company = Company.builder().companySymbol("AAPL").build();

        when(companyRepo.findAllByCompanySymbolIn(companiesSymbol))
                .thenReturn(singletonList(company));

        crawlingService.crawlGivenCompanies(companiesSymbol);

        verify(ratiosCrawlerService).crawlAllMarginData(company);
        verify(balanceSheetCrawlerService).crawlBalanceSheet(company);
        verify(incomeStatementCrawlerService).crawlIncomeStatement(company);
        verify(cashFlowStatementCrawlerService).crawlCashFlowStatement(company);
    }

}
