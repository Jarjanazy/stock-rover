package com.jalil.stockrover.crawler.margins;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

@SpringBootTest
public class MarginsCrawlerServiceLiveTest
{
    @Autowired
    private MarginsCrawlerService marginsCrawlerService;

    @Test
    public void givenTheAppleStockPageOnMacroTrends_ThenGetIt() throws IOException
    {
        marginsCrawlerService.crawlGrossMargin("MSFT", "microsoft");
    }
}