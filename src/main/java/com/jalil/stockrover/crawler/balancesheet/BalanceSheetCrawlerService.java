package com.jalil.stockrover.crawler.balancesheet;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.crawler.convertor.ToEntityConvertor;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jalil.stockrover.common.util.Utils.getDateFromString;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceSheetCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    private final IBalanceSheetRepo balanceSheetRepo;

    private final FilterService filterService;

    private final ToEntityConvertor toEntityConvertor;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public void crawlBalanceSheet(Company company) throws IOException
    {
        HtmlPage htmlPage =
                htmlPageFetcher.getBalanceSheetHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<LinkedTreeMap<String, String>> data = toDataStructureConvertor.getDataFromTable(htmlPage);

        List<String> filteredDates = filterService.filterDatesBiggerThanOnesInDB(data, company, BalanceSheet.class);

        List<BalanceSheet> balanceSheets = toEntityConvertor.mapToBalanceSheets(data, filteredDates, company);

        balanceSheetRepo.saveAll(balanceSheets);
    }

}
