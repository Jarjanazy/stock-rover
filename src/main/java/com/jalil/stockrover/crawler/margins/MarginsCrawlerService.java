package com.jalil.stockrover.crawler.margins;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.RowToEntityConvertor;
import com.jalil.stockrover.domain.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.grossmargin.IGrossMarginRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.jalil.stockrover.crawler.RowToEntityConvertor.rowToGrossMargin;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarginsCrawlerService
{
    private final IGrossMarginRepo grossMarginRepo;

    private final HtmlPageFetcher htmlPageFetcher;

    public void crawlGrossMargin(String stockSymbol, String companyName) throws IOException
    {
        HtmlPage page = htmlPageFetcher.getGrossMarginsHtmlPage(stockSymbol, companyName);

        DomElement domElement = getFirstTableWithGivenId(page, "style-1");

        DomNodeList<HtmlElement> rows = domElement.getElementsByTagName("tr");

        List<GrossMargin> grossMargins = htmlTableRowsToGrossMargins(rows);

        grossMarginRepo.saveAll(grossMargins);

    }


    private DomElement getFirstTableWithGivenId(HtmlPage page, String id)
    {
        return page
                .getElementById(id)
                .getElementsByTagName("tbody")
                .get(0);
    }

    private List<GrossMargin> htmlTableRowsToGrossMargins(DomNodeList<HtmlElement> rows)
    {
        return rows
                .stream()
                .map(row -> row.getElementsByTagName("td"))
                .map(RowToEntityConvertor::rowToGrossMargin)
                .collect(Collectors.toList());

    }

}
