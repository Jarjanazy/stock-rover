package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.netmargin.NetMargin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RowToEntityConvertor
{

    public static GrossMargin rowToGrossMargin(DomNodeList<HtmlElement> row, Company company)
    {
        LocalDate dateTime = getDateFromRow(row);

        double ttmRevenue = extractDoubleFromRowString(getCellValueFromRowUsingIndex(row, 1));
        double ttmGrossProfit = extractDoubleFromRowString(getCellValueFromRowUsingIndex(row, 2));
        double grossMargin = extractPercentageFromRowString(getCellValueFromRowUsingIndex(row, 3));

        return GrossMargin
                .builder()
                .date(dateTime.atStartOfDay())
                .ttmRevenue(ttmRevenue)
                .ttmGrossProfit(ttmGrossProfit)
                .grossMarginPercentage(grossMargin)
                .company(company)
                .build();
    }

    public static NetMargin rowToNetMargin(DomNodeList<HtmlElement> row, Company company)
    {
        LocalDate dateTime = getDateFromRow(row);

        double ttmRevenue = extractDoubleFromRowString(getCellValueFromRowUsingIndex(row, 1));
        double ttmNetIncome = extractDoubleFromRowString(getCellValueFromRowUsingIndex(row, 2));
        double netMargin = extractPercentageFromRowString(getCellValueFromRowUsingIndex(row, 3));

        return NetMargin
                .builder()
                .date(dateTime.atStartOfDay())
                .ttmRevenue(ttmRevenue)
                .ttmNetIncome(ttmNetIncome)
                .netMarginPercentage(netMargin)
                .company(company)
                .build();
    }

    private static LocalDate getDateFromRow(DomNodeList<HtmlElement> row)
    {
        String date = getCellValueFromRowUsingIndex(row, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
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
