package com.jalil.stockrover.crawler.stockscreener;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.jalil.stockrover.common.WebClientFactory.createWebClient;

@Service
@RequiredArgsConstructor
public class StockScreenerCrawlerService
{
    private final ICompanyRepo companyRepo;

    private final SheetToEntityConvertor sheetToEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlStockScreener() throws IOException
    {
        URL input = getClass().getResource("/htmlPage/stockScreenerTestPage.html").openConnection().getURL();

        HtmlPage htmlPage = createWebClient().getPage(input);

        List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

        List<Company> companies = sheetToEntityConvertor.mapToCompanies(data);

        companyRepo.saveAll(companies);
    }
}
