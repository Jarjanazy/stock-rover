package com.jalil.stockrover.web.controller;

import com.jalil.stockrover.crawler.stockscreener.StockScreenerCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/crawler")
@RequiredArgsConstructor
public class CrawlingController
{
    private final StockScreenerCrawlerService stockScreenerCrawlerService;

    @PostMapping("/all-companies")
    public void crawlAllCompanies() throws IOException
    {
        stockScreenerCrawlerService.crawlStockScreener();
    }

}
