package com.jalil.stockrover.web.controller;

import com.jalil.stockrover.crawler.balancesheet.BalanceSheetCrawlerService;
import com.jalil.stockrover.crawler.stockscreener.StockScreenerCrawlerService;
import com.jalil.stockrover.web.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/crawler")
@RequiredArgsConstructor
public class CrawlingController
{
    private final StockScreenerCrawlerService stockScreenerCrawlerService;

    private final BalanceSheetCrawlerService balanceSheetCrawlerService;

    private final CrawlingService crawlingService;

    @PostMapping("/all-companies")
    public void crawlAllCompanies() throws IOException
    {
        stockScreenerCrawlerService.crawlStockScreener();
    }

    @PostMapping("/all-balance-sheets")
    public void crawlAllBalanceSheets()
    {
        balanceSheetCrawlerService.crawlAllBalanceSheets();
    }

    @PostMapping("/uncrawled-balance-sheets")
    public void crawlUnCrawledBalanceSheets()
    {
        balanceSheetCrawlerService.crawlUnCrawledBalanceSheets();
    }

    @PostMapping("/companies/all-data")
    public void crawlAllDataForGivenCompanies(@RequestBody List<String> companiesTickerSymbols)
    {
        crawlingService.crawlGivenCompanies(companiesTickerSymbols);
    }

}
