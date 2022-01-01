package com.jalil.stockrover.crawler.cashflowstatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.HtmlPageToMapConvertor;
import com.jalil.stockrover.crawler.convertor.MapToEntityConvertor;
import com.jalil.stockrover.domain.cashflowstatement.CashFlowStatement;
import com.jalil.stockrover.domain.cashflowstatement.ICashFlowStatementRepo;
import com.jalil.stockrover.domain.company.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.jalil.stockrover.crawler.WebClientFactory.createWebClientForTableRetrieval;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CashFlowStatementCrawlerServiceTest
{
    private CashFlowStatementCrawlerService cashFlowStatementCrawlerService;

    @Mock
    private HtmlPageFetcher htmlPageFetcher;

    @Mock
    private ICashFlowStatementRepo cashFlowStatementRepo;

    @Captor
    private ArgumentCaptor<List<CashFlowStatement>> captor;

    @BeforeEach
    public void setup()
    {
        HtmlPageToMapConvertor htmlPageToMapConvertor = new HtmlPageToMapConvertor();
        MapToEntityConvertor mapToEntityConvertor = new MapToEntityConvertor();
        cashFlowStatementCrawlerService = new CashFlowStatementCrawlerService(htmlPageFetcher, cashFlowStatementRepo, mapToEntityConvertor, htmlPageToMapConvertor);
    }


    @Test
    public void givenCashFlowStatementPageUrl_ThenConvertItToCashFlowStatement() throws IOException
    {
        URL input = getClass().getResource("/cashFlowStatementTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClientForTableRetrieval().getPage(input);

        when(htmlPageFetcher.getCashFlowStatementHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        cashFlowStatementCrawlerService.crawlCashFlowStatement(company);

        verify(cashFlowStatementRepo).saveAll(captor.capture());

        List<CashFlowStatement> captured = captor.getValue();

        assertThat(captured).hasSize(67);

        assertThat(captured.get(0).getNetIncome()).isEqualTo(20551L);
        assertThat(captured.get(0).getChangeInAssetsLiabilities()).isEqualTo(-5664);
        assertThat(captured.get(0).getOperatingActivitiesCashFlow()).isEqualTo(20200L);
        assertThat(captured.get(0).getLongTermInvestmentsNetChange()).isEqualTo(256L);
        assertThat(captured.get(0).getInvestingActivitiesCashFlow()).isEqualTo(835L);
        assertThat(captured.get(0).getNetCommonEquityIssuedRepurchased()).isEqualTo(-19204L);
        assertThat(captured.get(0).getNetTotalEquityIssuedRepurchased()).isEqualTo(-19204L);
        assertThat(captured.get(0).getOtherFinancialActivities()).isEqualTo(-758L);
        assertThat(captured.get(0).getTotalDepreciationAndAmortization()).isEqualTo(2989L);
        assertThat(captured.get(0).getNetCashFlow()).isEqualTo(653);
        assertThat(captured.get(0).getNetCurrentDebt()).isEqualTo(-2000L);
        assertThat(captured.get(0).getCompany()).isEqualTo(company);
    }
}
