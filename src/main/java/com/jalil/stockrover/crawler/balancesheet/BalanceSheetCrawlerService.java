package com.jalil.stockrover.crawler.balancesheet;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.crawler.convertor.ToEntityConvertor;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceSheetCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final IBalanceSheetRepo balanceSheetRepo;

    private final ToEntityConvertor toEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlBalanceSheet(Company company) throws IOException
    {
        HtmlPage htmlPage =
                htmlPageFetcher.getBalanceSheetHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

        List<BalanceSheet> balanceSheets = toEntityConvertor.mapToBalanceSheets(data, company);

        balanceSheetRepo.saveAll(balanceSheets);
    }
}
