package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.jalil.stockrover.domain.grossmargin.GrossMargin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RowToEntityConvertor
{

    public static GrossMargin rowToGrossMargin(DomNodeList<HtmlElement> row)
    {
        String date = getCellValueFromRowUsingIndex(row, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);

        double ttmRevenue = extractDoubleFromRowString(getCellValueFromRowUsingIndex(row, 1));
        double ttmGrossProfit = extractDoubleFromRowString(getCellValueFromRowUsingIndex(row, 2));
        double grossMargin = extractPercentageFromRowString(getCellValueFromRowUsingIndex(row, 3));

        return GrossMargin
                .builder()
                .date(dateTime.atStartOfDay())
                .ttmRevenue(ttmRevenue)
                .ttmGrossProfit(ttmGrossProfit)
                .grossMarginPercentage(grossMargin).build();
    }

    private static double extractDoubleFromRowString(String value)
    {
        return Double.parseDouble(value.substring(1, value.length()-1));
    }

    private static double extractPercentageFromRowString(String value)
    {
        return Double.parseDouble(value.substring(0,value.length()-1));
    }

    private static String getCellValueFromRowUsingIndex(DomNodeList<HtmlElement> rowElements, int index)
    {
        return rowElements.get(index).getFirstChild().getNodeValue();
    }
}
