package com.jalil.stockrover.crawler.incomestatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
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
import java.time.LocalDateTime;
import java.util.List;
import static com.jalil.stockrover.common.WebClientFactory.createWebClientForTableRetrieval;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class IncomeStatementCrawlerServiceFromHtmlPageTest
{
    private IncomeStatementCrawlerService incomeStatementCrawlerService;

    @Mock
    private HtmlPageFetcher htmlPageFetcher;

    @Mock
    private IIncomeStatementRepo iIncomeStatementRepo;

    @Mock
    DynamicDataRepo dynamicDataRepo;

    @Captor
    ArgumentCaptor<List<IncomeStatement>> argumentCaptor;


    @BeforeEach
    public void setup()
    {
        ToDataStructureConvertor toDataStructureConvertor = new ToDataStructureConvertor();
        SheetToEntityConvertor sheetToEntityConvertor = new SheetToEntityConvertor();

        FilterService filterService = new FilterService(dynamicDataRepo, toDataStructureConvertor);
        incomeStatementCrawlerService = new IncomeStatementCrawlerService(htmlPageFetcher, iIncomeStatementRepo, filterService, sheetToEntityConvertor, toDataStructureConvertor);
    }

    @Test
    public void givenIncomeStatementPageUrl_ThenConvertItToIncomeStatement() throws IOException, InterruptedException
    {
        URL input = getClass().getResource("/incomeStatementTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClientForTableRetrieval().getPage(input);

        when(htmlPageFetcher.getIncomeStatementHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(iIncomeStatementRepo.saveAll(argumentCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        incomeStatementCrawlerService.crawlIncomeStatement(company);

        List<IncomeStatement> captured = argumentCaptor.getValue();

        assertThat(captured).hasSize(67);
        IncomeStatement incomeStatement = captured.get(0);
        assertThat(incomeStatement.getDate()).isBefore(LocalDateTime.now());
        assertThat(incomeStatement.getRevenue()).isEqualTo(83360);
        assertThat(incomeStatement.getCostOfGoodsSold()).isEqualTo(48186);
        assertThat(incomeStatement.getGrossProfit()).isEqualTo(35174);
        assertThat(incomeStatement.getCompany()).isEqualTo(company);
    }
}
