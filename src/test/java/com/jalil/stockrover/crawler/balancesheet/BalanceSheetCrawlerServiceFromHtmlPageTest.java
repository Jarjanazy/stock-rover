package com.jalil.stockrover.crawler.balancesheet;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
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

import static com.jalil.stockrover.common.WebClientFactory.createWebClientForTableRetrieval;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BalanceSheetCrawlerServiceFromHtmlPageTest
{
    private BalanceSheetCrawlerService balanceSheetCrawlerService;

    @Mock
    private HtmlPageFetcher htmlPageFetcher;

    @Mock
    private IBalanceSheetRepo balanceSheetRepo;

    @Mock
    private ICompanyRepo companyRepo;

    @Mock
    private DynamicDataRepo dynamicDataRepo;

    @Captor
    private ArgumentCaptor<List<BalanceSheet>> balanceSheetCaptor;

    @BeforeEach
    public void setup()
    {
        ToDataStructureConvertor toDataStructureConvertor = new ToDataStructureConvertor();
        SheetToEntityConvertor sheetToEntityConvertor = new SheetToEntityConvertor();

        FilterService filterService = new FilterService(dynamicDataRepo, toDataStructureConvertor);
        this.balanceSheetCrawlerService = new BalanceSheetCrawlerService(htmlPageFetcher, balanceSheetRepo, companyRepo, dynamicDataRepo, filterService, sheetToEntityConvertor, toDataStructureConvertor);
    }

    @Test
    public void givenBalanceSheetPageUrl_ThenConvertItToBalanceSheet() throws IOException
    {
        URL input = getClass().getResource("/balanceSheetTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClientForTableRetrieval().getPage(input);

        when(htmlPageFetcher.getBalanceSheetHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        balanceSheetCrawlerService.crawlBalanceSheet(company);

        verify(balanceSheetRepo).saveAll(balanceSheetCaptor.capture());

        List<BalanceSheet> balanceSheets = balanceSheetCaptor.getValue();

        assertThat(balanceSheets).hasSize(67);
        BalanceSheet balanceSheet = balanceSheets.get(0);
        assertThat(balanceSheet.getCashOnHand()).isEqualTo(62639);
        assertThat(balanceSheet.getReceivables()).isEqualTo(51506);
        assertThat(balanceSheet.getShareHolderEquity()).isEqualTo(63090);
    }

}
