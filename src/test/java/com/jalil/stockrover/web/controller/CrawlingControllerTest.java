package com.jalil.stockrover.web.controller;

import com.jalil.stockrover.crawler.balancesheet.BalanceSheetCrawlerService;
import com.jalil.stockrover.crawler.stockscreener.StockScreenerCrawlerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CrawlingController.class)
public class CrawlingControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    StockScreenerCrawlerService stockScreenerCrawlerService;

    @MockBean
    BalanceSheetCrawlerService balanceSheetCrawlerService;

    @Test
    public void givenCrawlAllCompaniesEndPoint_WhenCalled_ThenReturn200() throws Exception
    {
        mockMvc.perform(post("/crawler/all-companies"))
                .andExpect(status().isOk());

        verify(stockScreenerCrawlerService).crawlStockScreener();
    }

    @Test
    public void givenCrawlAllBalanceSheetsEndPoint_WhenCalled_ThenReturn200() throws Exception
    {
        mockMvc.perform(post("/crawler/all-balance-sheets"))
                .andExpect(status().isOk());

        verify(balanceSheetCrawlerService).crawlAllBalanceSheets();
    }

}
