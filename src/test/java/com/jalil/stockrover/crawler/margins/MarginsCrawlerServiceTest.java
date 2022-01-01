package com.jalil.stockrover.crawler.margins;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.grossmargin.IGrossMarginRepo;
import com.jalil.stockrover.domain.netmargin.INetMarginRepo;
import com.jalil.stockrover.domain.netmargin.NetMargin;
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

import static com.jalil.stockrover.crawler.WebClientFactory.createWebClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MarginsCrawlerServiceTest
{
    private MarginsCrawlerService marginsCrawlerService;

    @Mock
    HtmlPageFetcher htmlPageFetcher;

    @Mock
    IGrossMarginRepo grossMarginRepo;

    @Mock
    INetMarginRepo netMarginRepo;

    @Captor
    ArgumentCaptor<List<GrossMargin>> grossMarginCaptor;

    @Captor
    ArgumentCaptor<List<NetMargin>> netMarginCaptor;

    @BeforeEach
    public void setup()
    {
        marginsCrawlerService = new MarginsCrawlerService(grossMarginRepo, netMarginRepo, htmlPageFetcher);
    }


    @Test
    public void givenAGrossMarginsPage_ThenConvertItToGrossMargin() throws IOException
    {
        URL input = getClass().getResource("/grossMarginTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClient().getPage(input);

        when(htmlPageFetcher.getGrossMarginsHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(grossMarginRepo.saveAll(grossMarginCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companySymbol("AAPL").companyName("Apple").build();

        marginsCrawlerService.crawlGrossMargin(company);

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
    public void givenANetMarginsPage_WhenItHas2Rows_ThenConvertItToNetMargin() throws IOException
    {
        URL input = getClass().getResource("/netMarginTestPage.html").openConnection().getURL();
        HtmlPage htmlPage = createWebClient().getPage(input);

        when(htmlPageFetcher.getNetMarginsHtmlPage("AAPL", "Apple")).thenReturn(htmlPage);

        when(netMarginRepo.saveAll(netMarginCaptor.capture())).thenReturn(null);

        Company company = Company.builder().companyName("Apple").companySymbol("AAPL").build();

        marginsCrawlerService.crawlNetMargin(company);

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

}
