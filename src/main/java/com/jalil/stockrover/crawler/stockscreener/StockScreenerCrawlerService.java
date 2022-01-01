package com.jalil.stockrover.crawler.stockscreener;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.MapToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import static com.jalil.stockrover.crawler.convertor.HtmlPageToMapConvertor.getDataFromTable;

@Service
@RequiredArgsConstructor
public class StockScreenerCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final ICompanyRepo companyRepo;

    private final MapToEntityConvertor mapToEntityConvertor;

    public void crawlStockScreener() throws IOException
    {
        HtmlPage htmlPage = htmlPageFetcher.getStockScreenerHtmlPage();

        List<LinkedTreeMap<String, String>> data = getDataFromTable(htmlPage);

        List<Company> companies = mapToEntityConvertor.mapToCompanies(data);

        companyRepo.saveAll(companies);
    }
}
