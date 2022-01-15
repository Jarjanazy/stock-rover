package com.jalil.stockrover.crawler.stockscreener;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.jalil.stockrover.common.WebClientFactory.createWebClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StockScreenerCrawlerServiceFromHtmlPageTest
{
    private StockScreenerCrawlerService stockScreenerCrawlerService;

    @Mock
    private ICompanyRepo companyRepo;

    @Captor
    ArgumentCaptor<List<Company>> captor;

    @BeforeEach
    public void setup()
    {
        ToDataStructureConvertor toDataStructureConvertor = new ToDataStructureConvertor();
        SheetToEntityConvertor sheetToEntityConvertor = new SheetToEntityConvertor();
        stockScreenerCrawlerService = new StockScreenerCrawlerService(companyRepo, sheetToEntityConvertor, toDataStructureConvertor);
    }

    @Test
    public void givenAStockScreenerPage_ThenReturnCompanies() throws IOException
    {
        URL input = getClass().getResource("/stockScreenerTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClient().getPage(input);

        stockScreenerCrawlerService.crawlStockScreener();

        verify(companyRepo).saveAll(captor.capture());

        List<Company> capturedCompanies = captor.getValue();

        assertThat(capturedCompanies).hasSize(6184);

        Company company = capturedCompanies.get(0);
        assertThat(company.getCompanySymbol()).isEqualTo("A");
        assertThat(company.getCompanyName()).isEqualTo("agilent-technologies");
        assertThat(company.getCompanyNameDisplay()).isEqualTo("Agilent Technologies");
        assertThat(company.getCountryCode()).isEqualTo("United States");
        assertThat(company.getExchange()).isEqualTo("NYSE");
        assertThat(company.getIndustry()).isEqualTo("Electrical Test Equipment");
    }
}
