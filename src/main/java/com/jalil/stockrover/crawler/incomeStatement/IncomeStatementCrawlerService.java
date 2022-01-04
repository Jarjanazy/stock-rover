package com.jalil.stockrover.crawler.incomeStatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.crawler.convertor.ToEntityConvertor;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.incomeStatement.IIncomeStatementRepo;
import com.jalil.stockrover.domain.incomeStatement.IncomeStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeStatementCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final IIncomeStatementRepo iIncomeStatementRepo;

    private final FilterService filterService;

    private final ToEntityConvertor toEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlIncomeStatement(Company company) throws IOException
    {
        HtmlPage htmlPage = htmlPageFetcher
                .getIncomeStatementHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

        List<String> filteredDates = filterService.filterDatesBiggerThanOnesInDB(data, company, IncomeStatement.class);

        List<IncomeStatement> incomeStatements = toEntityConvertor.mapToIncomeStatements(data, filteredDates, company);

        iIncomeStatementRepo.saveAll(incomeStatements);
    }
}
