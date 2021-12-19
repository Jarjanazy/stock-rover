package com.jalil.stockrover.crawler;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.incomeStatement.IncomeStatement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.lang.Double.parseDouble;

public class MapToEntityConvertor
{
    public static List<IncomeStatement> mapToIncomeStatements(List<LinkedTreeMap<String, String>> dataList, Company company)
    {
        List<String> dates = getDatesFromData(dataList);

        return dates
            .stream()
            .map(date -> createIncomeStatementFromDateAndData(date, dataList, company))
            .collect(Collectors.toList());
    }

    private static IncomeStatement createIncomeStatementFromDateAndData(String date, List<LinkedTreeMap<String, String>> dataList, Company company)
    {
        return IncomeStatement
                .builder()
                .revenue(parseDouble(dataList.get(0).get(date)))
                .costOfGoodsSold(parseDouble(dataList.get(1).get(date)))
                .grossProfit(parseDouble(dataList.get(2).get(date)))
                .researchAndDevelopmentExpenses(parseDouble(dataList.get(3).get(date)))
                .sgaExpenses(parseDouble(dataList.get(4).get(date)))
                .operatingExpenses(parseDouble(dataList.get(6).get(date)))
                .operatingIncome(parseDouble(dataList.get(7).get(date)))
                .totalNonOperatingExpenses(parseDouble(dataList.get(8).get(date)))
                .preTaxIncome(parseDouble(dataList.get(9).get(date)))
                .incomeTaxes(parseDouble(dataList.get(10).get(date)))
                .incomeAfterTaxes(parseDouble(dataList.get(11).get(date)))
                .continuousOperationsIncome(parseDouble(dataList.get(13).get(date)))
                .netIncome(parseDouble(dataList.get(15).get(date)))
                .ebitda(parseDouble(dataList.get(16).get(date)))
                .ebit(parseDouble(dataList.get(17).get(date)))
                .basicSharesOutstanding(parseDouble(dataList.get(18).get(date)))
                .sharesOutstanding(parseDouble(dataList.get(19).get(date)))
                .basicEps(parseDouble(dataList.get(20).get(date)))
                .eps(parseDouble(dataList.get(21).get(date)))
                .date(getDateString(date))
                .company(company)
                .build();
    }

    private static List<String> getDatesFromData(List<LinkedTreeMap<String, String>> dataList)
    {
        Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");

        return dataList
                .get(0)
                .keySet()
                .stream()
                .filter(key -> datePattern.matcher(key).matches())
                .collect(Collectors.toList());
    }

    private static LocalDateTime getDateString(String dateString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter).atStartOfDay();
    }
}
