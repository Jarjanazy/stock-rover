package com.jalil.stockrover.crawler.stockscreener;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.crawler.convertor.ToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockScreenerCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final ICompanyRepo companyRepo;

    private final ToEntityConvertor toEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlStockScreener() throws IOException
    {
        HtmlPage htmlPage = htmlPageFetcher.getStockScreenerHtmlPage();

        List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

        List<Company> companies = toEntityConvertor.mapToCompanies(data);

        companyRepo.saveAll(companies);
    }
}
