package com.jalil.stockrover.crawler.convertor;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlPageToMapConvertor
{

    public static List<LinkedTreeMap<String, String>> getDataFromTable(HtmlPage htmlPage)
    {
        Pattern p = Pattern.compile("originalData\\s=\\s\\[\\{.*}]");

        Matcher matcher = p.matcher(htmlPage.asXml());

        matcher.find();

        String data = matcher.group(0);

        data = data.replace("originalData = ", "");

        return new Gson().fromJson(data, List.class);
    }
}
