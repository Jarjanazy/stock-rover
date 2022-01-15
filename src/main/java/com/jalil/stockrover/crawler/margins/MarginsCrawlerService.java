package com.jalil.stockrover.crawler.margins;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.TableToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.margin.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.margin.grossmargin.IGrossMarginRepo;
import com.jalil.stockrover.domain.margin.netmargin.INetMarginRepo;
import com.jalil.stockrover.domain.margin.netmargin.NetMargin;
import com.jalil.stockrover.domain.margin.operatingMargin.IOperatingMarginRepo;
import com.jalil.stockrover.domain.margin.operatingMargin.OperatingMargin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class MarginsCrawlerService
{
    private final IGrossMarginRepo grossMarginRepo;

    private final INetMarginRepo netMarginRepo;

    private final IOperatingMarginRepo operatingMarginRepo;

    private final HtmlPageFetcher htmlPageFetcher;

    private final TableToEntityConvertor tableToEntityConvertor;

    public void crawlGrossMargin(Company company) throws IOException
    {
        HtmlPage page = htmlPageFetcher
                .getGrossMarginsHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<GrossMargin> grossMargins = tableToEntityConvertor.pageToGrossMargins(page, company);

        grossMarginRepo.saveAll(grossMargins);
    }

    public void crawlNetMargin(Company company) throws IOException
    {
        HtmlPage page = htmlPageFetcher.getNetMarginsHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<NetMargin> netMargins = tableToEntityConvertor.pageToNetMargin(page, company);

        netMarginRepo.saveAll(netMargins);
    }


    public void crawlOperatingMargin(Company company) throws IOException
    {
        HtmlPage page = htmlPageFetcher.getOperatingMarginsHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<OperatingMargin> operatingMargins = tableToEntityConvertor.pageToOperatingMargins(page, company);

        operatingMarginRepo.saveAll(operatingMargins);
    }
}
