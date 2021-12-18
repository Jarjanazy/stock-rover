package com.jalil.stockrover.crawler.incomeStatement;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeStatementCrawlerService
{
    private final HtmlPageFetcher htmlPageFetcher;

    public void crawlIncomeStatement(String stockSymbol, String companyName) throws IOException
    {
        HtmlPage htmlPage = htmlPageFetcher.getIncomeStatementHtmlPage(stockSymbol, companyName);

        List<HashMap<String, String>> data = getDataFromTable(htmlPage);

    }

    private List<HashMap<String, String>> getDataFromTable(HtmlPage htmlPage)
    {
        Pattern p = Pattern.compile("originalData\\s=\\s\\[\\{.*}]");

        Matcher matcher = p.matcher(htmlPage.asXml());

        matcher.find();

        String data = matcher.group(0);

        data = data.replace("originalData = ", "");

        return new Gson().fromJson(data, List.class);
    }
}
