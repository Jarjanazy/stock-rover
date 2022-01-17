package com.jalil.stockrover.web.service;

import com.jalil.stockrover.crawler.balancesheet.BalanceSheetCrawlerService;
import com.jalil.stockrover.crawler.cashflowstatement.CashFlowStatementCrawlerService;
import com.jalil.stockrover.crawler.incomeStatement.IncomeStatementCrawlerService;
import com.jalil.stockrover.crawler.ratio.RatiosCrawlerService;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingService
{
    private final ICompanyRepo companyRepo;

    private final RatiosCrawlerService ratiosCrawlerService;

    private final BalanceSheetCrawlerService balanceSheetCrawlerService;

    private final IncomeStatementCrawlerService incomeStatementCrawlerService;

    private final CashFlowStatementCrawlerService cashFlowStatementCrawlerService;

    public void crawlGivenCompanies(List<String> companiesTickerSymbols)
    {
        List<Company> companies = companyRepo.findAllByCompanySymbolIn(companiesTickerSymbols);

        companies.forEach(this::crawlGivenCompany);
    }

    private void crawlGivenCompany(Company company)
    {
        try
        {
            ratiosCrawlerService.crawlAllMarginData(company);
            balanceSheetCrawlerService.crawlBalanceSheet(company);
            incomeStatementCrawlerService.crawlIncomeStatement(company);
            cashFlowStatementCrawlerService.crawlCashFlowStatement(company);
        } catch (Exception e)
        {
            log.error("An exception happened while crawling company {}", company.getCompanyName());
        }

    }
}
