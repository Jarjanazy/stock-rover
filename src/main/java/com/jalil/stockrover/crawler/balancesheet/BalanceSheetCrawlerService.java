package com.jalil.stockrover.crawler.balancesheet;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.jalil.stockrover.common.MultiThreadWorker.runAsyncJobInParallel;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceSheetCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final IBalanceSheetRepo balanceSheetRepo;

    private final ICompanyRepo companyRepo;

    private final DynamicDataRepo dynamicDataRepo;

    private final FilterService filterService;

    private final SheetToEntityConvertor sheetToEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlAllBalanceSheets()
    {
        Runnable runnable = () -> StreamSupport
                .stream(companyRepo.findAll().spliterator(), true)
                .forEach(this::crawlBalanceSheet);

        runAsyncJobInParallel(3, runnable);
    }

    public void crawlUnCrawledBalanceSheets()
    {
        List<Company> unCrawledCompanies = dynamicDataRepo.findUnCrawledCompanies(BalanceSheet.class);

        Runnable runnable = () -> unCrawledCompanies.stream().parallel().forEach(this::crawlBalanceSheet);

        runAsyncJobInParallel(3, runnable);
    }

    public void crawlBalanceSheet(Company company)
    {
        try
        {
            log.info("Started crawling the balance sheet for {}", company.getCompanyName());

            HtmlPage htmlPage =
                    htmlPageFetcher.getBalanceSheetHtmlPage(company.getCompanySymbol(), company.getCompanyName());

            List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

            List<String> filteredDates = filterService.filterDatesBiggerThanOnesInDB(data, company, BalanceSheet.class);

            List<BalanceSheet> balanceSheets = sheetToEntityConvertor.mapToBalanceSheets(data, filteredDates, company);

            balanceSheetRepo.saveAll(balanceSheets);

            log.info("Done crawling the balance sheet for {}", company.getCompanyName());

        } catch (Exception e)
        {
            log.error("An error happened while crawling the balance sheet of the company {}",
                    company.getCompanyName(), e);
        }
    }
}
