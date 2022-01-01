package com.jalil.stockrover.crawler.cashflowstatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.MapToEntityConvertor;
import com.jalil.stockrover.domain.cashflowstatement.CashFlowStatement;
import com.jalil.stockrover.domain.cashflowstatement.ICashFlowStatementRepo;
import com.jalil.stockrover.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import static com.jalil.stockrover.crawler.convertor.HtmlPageToMapConvertor.getDataFromTable;

@Service
@RequiredArgsConstructor
public class CashFlowStatementCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final ICashFlowStatementRepo cashFlowStatementRepo;

    private final MapToEntityConvertor mapToEntityConvertor;

    public void crawlCashFlowStatement(Company company) throws IOException
    {
        HtmlPage htmlPage =
                htmlPageFetcher.getCashFlowStatementHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<LinkedTreeMap<String, String>> data = getDataFromTable(htmlPage);

        List<CashFlowStatement> cashFlowStatements = mapToEntityConvertor.mapToCashFlowStatements(data, company);

        cashFlowStatementRepo.saveAll(cashFlowStatements);
    }


}
