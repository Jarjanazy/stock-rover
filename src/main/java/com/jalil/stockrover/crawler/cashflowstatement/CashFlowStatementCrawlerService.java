package com.jalil.stockrover.crawler.cashflowstatement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
import com.jalil.stockrover.domain.cashflowstatement.CashFlowStatement;
import com.jalil.stockrover.domain.cashflowstatement.ICashFlowStatementRepo;
import com.jalil.stockrover.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashFlowStatementCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final ICashFlowStatementRepo cashFlowStatementRepo;

    private final FilterService filterService;

    private final SheetToEntityConvertor sheetToEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlCashFlowStatement(Company company) throws IOException
    {
        HtmlPage htmlPage =
                htmlPageFetcher.getCashFlowStatementHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

        List<String> filteredDates = filterService.filterDatesBiggerThanOnesInDB(data, company, CashFlowStatement.class);

        List<CashFlowStatement> cashFlowStatements = sheetToEntityConvertor.mapToCashFlowStatements(data, filteredDates, company);

        cashFlowStatementRepo.saveAll(cashFlowStatements);
    }


}
