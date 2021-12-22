package com.jalil.stockrover.crawler.margins;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.grossmargin.IGrossMarginRepo;
import com.jalil.stockrover.domain.netmargin.INetMarginRepo;
import com.jalil.stockrover.domain.netmargin.NetMargin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.jalil.stockrover.crawler.convertor.RowToEntityConvertor.rowToGrossMargin;
import static com.jalil.stockrover.crawler.convertor.RowToEntityConvertor.rowToNetMargin;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarginsCrawlerService
{
    private final IGrossMarginRepo grossMarginRepo;

    private final INetMarginRepo netMarginRepo;

    private final HtmlPageFetcher htmlPageFetcher;

    public void crawlGrossMargin(Company company) throws IOException
    {
        HtmlPage page = htmlPageFetcher
                .getGrossMarginsHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        DomElement domElement = getFirstTableWithGivenId(page, "style-1");

        DomNodeList<HtmlElement> rows = domElement.getElementsByTagName("tr");

        List<GrossMargin> grossMargins = htmlTableRowsToGrossMargins(rows, company);

        grossMarginRepo.saveAll(grossMargins);
    }

    public void crawlNetMargin(Company company) throws IOException
    {
        HtmlPage page = htmlPageFetcher.getNetMarginsHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        DomElement domElement = getFirstTableWithGivenId(page, "style-1");

        DomNodeList<HtmlElement> rows = domElement.getElementsByTagName("tr");

        List<NetMargin> netMargins = htmlTableRowsToNetMargins(rows, company);

        netMarginRepo.saveAll(netMargins);
    }


    private DomElement getFirstTableWithGivenId(HtmlPage page, String id)
    {
        return page
                .getElementById(id)
                .getElementsByTagName("tbody")
                .get(0);
    }

    private List<GrossMargin> htmlTableRowsToGrossMargins(DomNodeList<HtmlElement> rows, Company company)
    {
        return rows
                .stream()
                .map(row -> row.getElementsByTagName("td"))
                .map(row -> rowToGrossMargin(row, company))
                .collect(Collectors.toList());
    }

    private List<NetMargin> htmlTableRowsToNetMargins(DomNodeList<HtmlElement> rows, Company company)
    {
        return rows
                .stream()
                .map(row -> row.getElementsByTagName("td"))
                .map(row -> rowToNetMargin(row, company))
                .collect(Collectors.toList());
    }

}
