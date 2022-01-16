package com.jalil.stockrover.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalil.stockrover.crawler.balancesheet.BalanceSheetCrawlerService;
import com.jalil.stockrover.crawler.stockscreener.StockScreenerCrawlerService;
import com.jalil.stockrover.web.service.CrawlingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CrawlingController.class)
public class CrawlingControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StockScreenerCrawlerService stockScreenerCrawlerService;

    @MockBean
    BalanceSheetCrawlerService balanceSheetCrawlerService;

    @MockBean
    CrawlingService crawlingService;

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

    @Test
    public void givenCrawlUnCrawledCompaniesBalanceSheetsEndpoint_WhenCalled_ThenReturn200() throws Exception
    {
        mockMvc.perform(post("/crawler/uncrawled-balance-sheets"))
                .andExpect(status().isOk());

        verify(balanceSheetCrawlerService).crawlUnCrawledBalanceSheets();

    }

    @Test
    public void givenAListOf3Companies_WhenCrawlAllDataEndpointIsCalled_ThenCrawlThem() throws Exception
    {
        List<String> companies = asList("AAPL", "MSFT", "GOOGLE");

        mockMvc.perform(post("/crawler/companies/all-data")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companies)))
        .andExpect(status().isOk());

        verify(crawlingService).crawlGivenCompanies(companies);
    }

}
