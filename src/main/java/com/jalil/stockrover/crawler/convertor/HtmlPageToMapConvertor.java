package com.jalil.stockrover.crawler.convertor;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HtmlPageToMapConvertor
{
    public List<LinkedTreeMap<String, String>> getDataFromTable(HtmlPage htmlPage)
    {
        Pattern p = Pattern.compile("originalData\\s=\\s\\[\\{[\\s\\S]*}]");

        Matcher matcher = p.matcher(htmlPage.asXml());

        matcher.find();

        String data = matcher.group(0);

        data = data.replace("originalData = ", "");

        //replace everything between (,"name_link") and (&gt;") with empty string
        data = data.replaceAll(",\"name_link\"([\\s\\S]*?)(\\&gt;\")", "");

        return new Gson().fromJson(data, List.class);
    }
}
