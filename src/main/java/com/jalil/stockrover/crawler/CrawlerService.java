package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrawlerService
{

    public void getGivenPageUsingURL(String url) throws IOException
    {
        var webClient = createWebClient();

        HtmlPage page = webClient.getPage(url);

        DomElement domElement = getFirstTableWithGivenId(page, "style-1");

        DomNodeList<HtmlElement> rows = domElement.getElementsByTagName("tr");

        Map<String, String> dateGrossMarginMap = rows
                .stream()
                .map(row -> row.getElementsByTagName("td"))
                .collect(Collectors.toMap(rowElements -> rowElements.get(0).getFirstChild().getNodeValue(),
                                        rowElements -> rowElements.get(3).getFirstChild().getNodeValue()));
    }

    private DomElement getFirstTableWithGivenId(HtmlPage page, String id)
    {
        return page
                .getElementById(id)
                .getElementsByTagName("tbody")
                .get(0);
    }


    private WebClient createWebClient()
    {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.setCookieManager(new CookieManager());
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.addRequestHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        webClient.addRequestHeader("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36");
        webClient.addRequestHeader("accept-encoding", "gzip, deflate, sdch");
        webClient.addRequestHeader("accept-language", "en-US,en;q=0.8");
        webClient.addRequestHeader("https", "1");
        return webClient;
    }


}
