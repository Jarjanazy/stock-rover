package com.jalil.stockrover.crawler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class CrawlerServiceLiveTest
{
    @Autowired
    private CrawlerService crawlerService;

    @Test
    public void givenTheAppleStockPageOnMacroTrends_ThenGetIt() throws IOException
    {
        crawlerService.getGivenPageUsingURL("https://www.macrotrends.net/stocks/charts/MSFT/microsoft/gross-margin");
    }
}
