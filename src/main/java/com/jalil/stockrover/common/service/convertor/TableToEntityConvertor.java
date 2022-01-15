package com.jalil.stockrover.common.service.convertor;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.grossmargin.GrossMargin;
import com.jalil.stockrover.domain.netmargin.NetMargin;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


// table has both date and data in the columns (date | ttmRevenue | grossMargin)
@Service
public class TableToEntityConvertor
{

    public List<GrossMargin> pageToGrossMargins(HtmlPage page, Company company)
    {
        DomElement domElement = getFirstTableWithGivenId(page, "style-1");

        DomNodeList<HtmlElement> rows = domElement.getElementsByTagName("tr");

        return htmlTableRowsToGrossMargins(rows, company);
    }

    public List<NetMargin> pageToNetMargin(HtmlPage page, Company company)
    {
        DomElement domElement = getFirstTableWithGivenId(page, "style-1");

        DomNodeList<HtmlElement> rows = domElement.getElementsByTagName("tr");

        return htmlTableRowsToNetMargins(rows, company);
    }

    private DomElement getFirstTableWithGivenId(HtmlPage page, String id)
    {
        return page
                .getElementById(id)
                .getElementsByTagName("tbody")
                .get(0);
    }

    private List<NetMargin> htmlTableRowsToNetMargins(DomNodeList<HtmlElement> rows, Company company)
    {
        return rows
                .stream()
                .map(row -> row.getElementsByTagName("td"))
                .map(row -> rowToNetMargin(row, company))
                .collect(Collectors.toList());
    }

    private List<GrossMargin> htmlTableRowsToGrossMargins(DomNodeList<HtmlElement> rows, Company company)
    {
        return rows
                .stream()
                .map(row -> row.getElementsByTagName("td"))
                .map(row -> rowToGrossMargin(row, company))
                .collect(Collectors.toList());
    }


    private GrossMargin rowToGrossMargin(DomNodeList<HtmlElement> row, Company company)
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

    private NetMargin rowToNetMargin(DomNodeList<HtmlElement> row, Company company)
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

    private LocalDate getDateFromRow(DomNodeList<HtmlElement> row)
    {
        String date = getCellValueFromRowUsingIndex(row, 0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private double extractDoubleFromRowString(String value)
    {
        return Double.parseDouble(value.substring(1, value.length()-1));
    }

    private double extractPercentageFromRowString(String value)
    {
        return Double.parseDouble(value.substring(0,value.length()-1));
    }

    private String getCellValueFromRowUsingIndex(DomNodeList<HtmlElement> rowElements, int index)
    {
        return rowElements.get(index).getFirstChild().getNodeValue();
    }
}
