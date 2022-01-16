package com.jalil.stockrover.crawler.ratio;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.TableToEntityConvertor;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.ratio.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.ratio.grossmargin.IGrossMarginRepo;
import com.jalil.stockrover.domain.ratio.netmargin.INetMarginRepo;
import com.jalil.stockrover.domain.ratio.netmargin.NetMargin;
import com.jalil.stockrover.domain.ratio.operatingMargin.IOperatingMarginRepo;
import com.jalil.stockrover.domain.ratio.operatingMargin.OperatingMargin;
import com.jalil.stockrover.domain.ratio.roa.IROARepo;
import com.jalil.stockrover.domain.ratio.roa.ROA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RatiosCrawlerService
{
    private final IGrossMarginRepo grossMarginRepo;

    private final INetMarginRepo netMarginRepo;

    private final IOperatingMarginRepo operatingMarginRepo;

    private final IROARepo roaRepo;

    private final HtmlPageFetcher htmlPageFetcher;

    private final TableToEntityConvertor tableToEntityConvertor;

    public void crawlAllMarginData(Company company) throws IOException
    {
        crawlRoa(company);
        crawlOperatingMargin(company);
        crawlGrossMargin(company);
        crawlNetMargin(company);
    }

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

    public void crawlRoa(Company company) throws IOException
    {
        HtmlPage page = htmlPageFetcher.getROAHtmlPage(company.getCompanySymbol(), company.getCompanyName());

        List<ROA> roas = tableToEntityConvertor.pageToROA(page, company);

        roaRepo.saveAll(roas);
    }
}
