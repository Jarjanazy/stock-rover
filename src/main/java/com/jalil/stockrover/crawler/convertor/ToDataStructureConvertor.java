package com.jalil.stockrover.crawler.convertor;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ToDataStructureConvertor
{
    public List<LinkedTreeMap<String, String>> getDataFromTable(HtmlPage htmlPage)
    {
        Pattern p = Pattern.compile("originalData\\s=\\s\\[\\{[\\s\\S]*}]");

        Matcher matcher = p.matcher(htmlPage.asXml());

        matcher.find();

        String data = matcher.group(0);

        data = data.replace("originalData = ", "");

        //replace everything between (,"name_link") and (&gt;") with empty string
        data = data.replaceAll(",\"name_link\"([\\s\\S]+?)(\\&gt;\")", "");

        return new Gson().fromJson(data, List.class);
    }

    public List<String> getDatesFromData(List<LinkedTreeMap<String, String>> dataList)
    {
        Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");

        return dataList
                .get(0)
                .keySet()
                .stream()
                .filter(key -> datePattern.matcher(key).matches())
                .collect(Collectors.toList());
    }
}
