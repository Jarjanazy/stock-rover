package com.jalil.stockrover.crawler.incomeStatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.MapToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.incomeStatement.IIncomeStatementRepo;
import com.jalil.stockrover.domain.incomeStatement.IncomeStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import static com.jalil.stockrover.crawler.convertor.HtmlPageToMapConvertor.getDataFromTable;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeStatementCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final IIncomeStatementRepo iIncomeStatementRepo;

    private final MapToEntityConvertor mapToEntityConvertor;

    public void crawlIncomeStatement(Company company) throws IOException
    {
        HtmlPage htmlPage = htmlPageFetcher
                .getIncomeStatementHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<LinkedTreeMap<String, String>> data = getDataFromTable(htmlPage);

        List<IncomeStatement> incomeStatements = mapToEntityConvertor.mapToIncomeStatements(data, company);

        iIncomeStatementRepo.saveAll(incomeStatements);
    }
}
