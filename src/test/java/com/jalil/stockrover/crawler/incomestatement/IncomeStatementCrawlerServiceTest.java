package com.jalil.stockrover.crawler.incomestatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.incomeStatement.IncomeStatementCrawlerService;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.incomeStatement.IIncomeStatementRepo;
import com.jalil.stockrover.domain.incomeStatement.IncomeStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static com.jalil.stockrover.crawler.WebClientFactory.createWebClient;
import static com.jalil.stockrover.crawler.WebClientFactory.createWebClientForTableRetrieval;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class IncomeStatementCrawlerServiceTest
{
    private IncomeStatementCrawlerService incomeStatementCrawlerService;

    @Mock
    private HtmlPageFetcher htmlPageFetcher;

    @Mock
    private IIncomeStatementRepo iIncomeStatementRepo;

    @Captor
    ArgumentCaptor<List<IncomeStatement>> argumentCaptor;


    @BeforeEach
    public void setup()
    {
        incomeStatementCrawlerService = new IncomeStatementCrawlerService(htmlPageFetcher, iIncomeStatementRepo);
    }

    @Test
    public void givenIncomeStatementPageUrl_WhenItHas2RowsAnd2Columns_ThenConvertItToIncomeStatement() throws IOException, InterruptedException
    {
        URL input = getClass().getResource("/incomeStatementTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClientForTableRetrieval().getPage(input);

        when(htmlPageFetcher.getIncomeStatementHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(iIncomeStatementRepo.saveAll(argumentCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        incomeStatementCrawlerService.crawlIncomeStatement(company);

        List<IncomeStatement> captured = argumentCaptor.getValue();

        assertThat(captured).hasSize(67);

    }


}
