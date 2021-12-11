package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CrawlerService
{

    public void getGivenPageUsingURL(String url) throws IOException
    {
        var webClient = createWebClient();

        Page page = webClient.getPage(url);

        log.info("Loaded the URL {} with the content {}", url, page.getWebResponse().getContentAsString());

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
