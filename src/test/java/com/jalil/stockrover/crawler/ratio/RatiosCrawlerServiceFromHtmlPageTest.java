package com.jalil.stockrover.crawler.ratio;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.TableToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.ratio.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.ratio.grossmargin.IGrossMarginRepo;
import com.jalil.stockrover.domain.ratio.netmargin.INetMarginRepo;
import com.jalil.stockrover.domain.ratio.netmargin.NetMargin;
import com.jalil.stockrover.domain.ratio.operatingMargin.IOperatingMarginRepo;
import com.jalil.stockrover.domain.ratio.operatingMargin.OperatingMargin;
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

import static com.jalil.stockrover.common.WebClientFactory.createWebClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RatiosCrawlerServiceFromHtmlPageTest
{
    private RatiosCrawlerService ratiosCrawlerService;

    @Mock
    HtmlPageFetcher htmlPageFetcher;

    @Mock
    IGrossMarginRepo grossMarginRepo;

    @Mock
    INetMarginRepo netMarginRepo;

    @Mock
    IOperatingMarginRepo operatingMarginRepo;

    @Captor
    ArgumentCaptor<List<GrossMargin>> grossMarginCaptor;

    @Captor
    ArgumentCaptor<List<NetMargin>> netMarginCaptor;

    @Captor
    ArgumentCaptor<List<OperatingMargin>> operatingMarginCaptor;

    @BeforeEach
    public void setup()
    {
        TableToEntityConvertor tableToEntityConvertor = new TableToEntityConvertor();
        ratiosCrawlerService = new RatiosCrawlerService(grossMarginRepo, netMarginRepo, operatingMarginRepo, htmlPageFetcher, tableToEntityConvertor);
    }


    @Test
    public void givenAGrossMarginsPage_ThenConvertItToGrossMargin() throws IOException
    {
        URL input = getClass().getResource("/grossMarginTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClient().getPage(input);

        when(htmlPageFetcher.getGrossMarginsHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(grossMarginRepo.saveAll(grossMarginCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companySymbol("AAPL").companyName("Apple").build();

        ratiosCrawlerService.crawlGrossMargin(company);

        List<GrossMargin> captured = grossMarginCaptor.getValue();

        assertThat(captured).hasSize(1);

        assertThat(captured.get(0).getGrossMarginPercentage()).isEqualTo(68.86);
        assertThat(captured.get(0).getTtmGrossProfit()).isEqualTo(121.38);
        assertThat(captured.get(0).getTtmRevenue()).isEqualTo(176.25);
        assertThat(captured.get(0).getCompany()).isEqualTo(company);

        LocalDateTime date = captured.get(0).getDate();
        assertThat(date.getDayOfMonth()).isEqualTo(2);
        assertThat(date.getMonthValue()).isEqualTo(1);
        assertThat(date.getYear()).isEqualTo(2021);
    }

    @Test
    public void givenANetMarginsPage_ThenConvertItToNetMargin() throws IOException
    {
        URL input = getClass().getResource("/netMarginTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClient().getPage(input);

        when(htmlPageFetcher.getNetMarginsHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(netMarginRepo.saveAll(netMarginCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        ratiosCrawlerService.crawlNetMargin(company);

        List<NetMargin> captured = netMarginCaptor.getValue();

        assertThat(captured).hasSize(2);

        assertThat(captured.get(1).getNetMarginPercentage()).isEqualTo(69.86);
        assertThat(captured.get(1).getTtmNetIncome()).isEqualTo(122.38);
        assertThat(captured.get(1).getTtmRevenue()).isEqualTo(174.25);
        assertThat(captured.get(1).getCompany()).isNotNull();

        LocalDateTime date = captured.get(1).getDate();
        assertThat(date.getDayOfMonth()).isEqualTo(2);
        assertThat(date.getMonthValue()).isEqualTo(1);
        assertThat(date.getYear()).isEqualTo(2022);

    }

    @Test
    public void givenAnOperatingMarginPage_ThenConvertItToOperatingMargin() throws IOException
    {
        URL input = getClass().getResource("/operatingMarginTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClient().getPage(input);

        when(htmlPageFetcher.getOperatingMarginsHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(operatingMarginRepo.saveAll(operatingMarginCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        ratiosCrawlerService.crawlOperatingMargin(company);

        List<OperatingMargin> captured = operatingMarginCaptor.getValue();

        assertThat(captured).hasSize(2);

        assertThat(captured.get(0).getTtmRevenue()).isEqualTo(176.25);
        assertThat(captured.get(0).getTtmOperatingIncome()).isEqualTo(74.28);
        assertThat(captured.get(0).getOperatingMarginPercentage()).isEqualTo(42.14);
        assertThat(captured.get(0).getCompany()).isEqualTo(company);

        LocalDateTime date = captured.get(0).getDate();
        assertThat(date.getDayOfMonth()).isEqualTo(30);
        assertThat(date.getMonthValue()).isEqualTo(9);
        assertThat(date.getYear()).isEqualTo(2021);
    }

}
