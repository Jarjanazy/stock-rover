package com.jalil.stockrover.common;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;

public class WebClientFactory
{
    public static WebClient createWebClient()
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

    public static WebClient createWebClientForTableRetrieval()
    {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.setCookieManager(new CookieManager());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.addRequestHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        webClient.addRequestHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        webClient.addRequestHeader("accept-encoding", "gzip, deflate, sdch");
        webClient.addRequestHeader("accept-language", "en-US,en;q=0.8");
        webClient.addRequestHeader("https", "1");
        return webClient;
    }
}
