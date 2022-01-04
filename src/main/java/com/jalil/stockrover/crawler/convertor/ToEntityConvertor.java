package com.jalil.stockrover.crawler.convertor;

import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.cashflowstatement.CashFlowStatement;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.incomeStatement.IncomeStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.jalil.stockrover.common.util.Utils.getDateFromString;
import static java.lang.Double.parseDouble;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToEntityConvertor
{
    private final ToDataStructureConvertor toDataStructureConvertor;

    public List<Company> mapToCompanies(List<LinkedTreeMap<String, String>> dataList)
    {
        return dataList
                .stream()
                .map(this::mapToCompany)
                .collect(Collectors.toList());
    }

    public List<IncomeStatement> mapToIncomeStatements(List<LinkedTreeMap<String, String>> dataList, List<String> dates, Company company)
    {
        return dates
            .stream()
            .map(date -> createIncomeStatementFromDateAndData(date, dataList, company))
            .collect(Collectors.toList());
    }

    public List<BalanceSheet> mapToBalanceSheets(List<LinkedTreeMap<String, String>> dataList, List<String> dates, Company company)
    {
        return dates
                .stream()
                .map(date -> createBalanceSheetFromDateAndData(date, dataList, company))
                .collect(Collectors.toList());
    }

    public List<CashFlowStatement> mapToCashFlowStatements(List<LinkedTreeMap<String, String>> dataList, List<String> dates, Company company)
    {
        return dates
                .stream()
                .map(date -> createCashFlowStatementFromDataAndDate(date, dataList, company))
                .collect(Collectors.toList());
    }

    private Company mapToCompany(LinkedTreeMap<String, String> data)
    {
        return Company
                .builder()
                .companySymbol(data.get("ticker"))
                .companyName(data.get("comp_name"))
                .companyNameDisplay(data.get("comp_name_2"))
                .industry(data.get("zacks_x_ind_desc"))
                .exchange(data.get("exchange"))
                .countryCode(data.get("country_code"))
                .build();
    }

    private CashFlowStatement createCashFlowStatementFromDataAndDate(String date, List<LinkedTreeMap<String, String>> dataList, Company company)
    {
        return CashFlowStatement
                .builder()
                .netIncome(parseDoubleOrGet0(dataList.get(0).get(date)))
                .totalDepreciationAndAmortization(parseDoubleOrGet0(dataList.get(1).get(date)))
                .otherNonCashItems(parseDoubleOrGet0(dataList.get(2).get(date)))
                .totalNonCashItems(parseDoubleOrGet0(dataList.get(3).get(date)))
                .changeInAccountsReceivable(parseDoubleOrGet0(dataList.get(4).get(date)))
                .changeInInventories(parseDoubleOrGet0(dataList.get(5).get(date)))
                .changeInAccountsPayable(parseDoubleOrGet0(dataList.get(6).get(date)))
                .changeInAssetsLiabilities(parseDoubleOrGet0(dataList.get(7).get(date)))

                .operatingActivitiesCashFlow(parseDoubleOrGet0(dataList.get(9).get(date)))
                .propertyPlantEquipmentNetChange(parseDoubleOrGet0(dataList.get(10).get(date)))
                .intangibleAssetsNetChange(parseDoubleOrGet0(dataList.get(11).get(date)))
                .netAcquisitionsDivestitures(parseDoubleOrGet0(dataList.get(12).get(date)))
                .shortTermInvestmentsNetChange(parseDoubleOrGet0(dataList.get(13).get(date)))
                .longTermInvestmentsNetChange(parseDoubleOrGet0(dataList.get(14).get(date)))

                .otherInvestingActivities(parseDoubleOrGet0(dataList.get(16).get(date)))

                .investingActivitiesCashFlow(parseDoubleOrGet0(dataList.get(17).get(date)))
                .netLongTermDebt(parseDoubleOrGet0(dataList.get(18).get(date)))
                .netCurrentDebt(parseDoubleOrGet0(dataList.get(19).get(date)))
                .debtIssuanceRetirementNetTotal(parseDoubleOrGet0(dataList.get(20).get(date)))
                .netCommonEquityIssuedRepurchased(parseDoubleOrGet0(dataList.get(21).get(date)))
                .netTotalEquityIssuedRepurchased(parseDoubleOrGet0(dataList.get(22).get(date)))
                .otherFinancialActivities(parseDoubleOrGet0(dataList.get(24).get(date)))
                .financialActivitiesCashFlow(parseDoubleOrGet0(dataList.get(25).get(date)))
                .netCashFlow(parseDoubleOrGet0(dataList.get(26).get(date)))
                .date(getDateFromString(date))
                .company(company)
                .build();
    }

    private BalanceSheet createBalanceSheetFromDateAndData(String date, List<LinkedTreeMap<String, String>> dataList, Company company)
    {
        return BalanceSheet
                .builder()
                .cashOnHand(parseDoubleOrGet0(dataList.get(0).get(date)))
                .receivables(parseDoubleOrGet0(dataList.get(1).get(date)))
                .inventory(parseDoubleOrGet0(dataList.get(2).get(date)))
                .otherCurrentAssets(parseDoubleOrGet0(dataList.get(4).get(date)))
                .totalCurrentAssets(parseDoubleOrGet0(dataList.get(5).get(date)))
                .propertyPlantEquipment(parseDoubleOrGet0(dataList.get(6).get(date)))
                .longTermInvestments(parseDoubleOrGet0(dataList.get(7).get(date)))
                .otherLongTermAssets(parseDoubleOrGet0(dataList.get(9).get(date)))
                .totalLongTermAssets(parseDoubleOrGet0(dataList.get(10).get(date)))
                .totalAssets(parseDoubleOrGet0(dataList.get(11).get(date)))
                .totalCurrentLiabilities(parseDoubleOrGet0(dataList.get(12).get(date)))
                .longTermDebt(parseDoubleOrGet0(dataList.get(13).get(date)))
                .otherNonCurrentLiabilities(parseDoubleOrGet0(dataList.get(14).get(date)))
                .totalLongTermLiabilities(parseDoubleOrGet0(dataList.get(15).get(date)))
                .totalLiabilities(parseDoubleOrGet0(dataList.get(16).get(date)))
                .commonStockNet(parseDoubleOrGet0(dataList.get(17).get(date)))
                .retainedEarnings(parseDoubleOrGet0(dataList.get(18).get(date)))
                .comprehensiveIncome(parseDoubleOrGet0(dataList.get(19).get(date)))
                .shareHolderEquity(parseDoubleOrGet0(dataList.get(21).get(date)))
                .date(getDateFromString(date))
                .company(company)
                .build();
    }

    private IncomeStatement createIncomeStatementFromDateAndData(String date, List<LinkedTreeMap<String, String>> dataList, Company company)
    {
        return IncomeStatement
                .builder()
                .revenue(parseDoubleOrGet0(dataList.get(0).get(date)))
                .costOfGoodsSold(parseDoubleOrGet0(dataList.get(1).get(date)))
                .grossProfit(parseDoubleOrGet0(dataList.get(2).get(date)))
                .researchAndDevelopmentExpenses(parseDoubleOrGet0(dataList.get(3).get(date)))
                .sgaExpenses(parseDoubleOrGet0(dataList.get(4).get(date)))
                .operatingExpenses(parseDoubleOrGet0(dataList.get(6).get(date)))
                .operatingIncome(parseDoubleOrGet0(dataList.get(7).get(date)))
                .totalNonOperatingExpenses(parseDoubleOrGet0(dataList.get(8).get(date)))
                .preTaxIncome(parseDoubleOrGet0(dataList.get(9).get(date)))
                .incomeTaxes(parseDoubleOrGet0(dataList.get(10).get(date)))
                .incomeAfterTaxes(parseDoubleOrGet0(dataList.get(11).get(date)))
                .continuousOperationsIncome(parseDoubleOrGet0(dataList.get(13).get(date)))
                .netIncome(parseDoubleOrGet0(dataList.get(15).get(date)))
                .ebitda(parseDoubleOrGet0(dataList.get(16).get(date)))
                .ebit(parseDoubleOrGet0(dataList.get(17).get(date)))
                .basicSharesOutstanding(parseDoubleOrGet0(dataList.get(18).get(date)))
                .sharesOutstanding(parseDoubleOrGet0(dataList.get(19).get(date)))
                .basicEps(parseDoubleOrGet0(dataList.get(20).get(date)))
                .eps(parseDoubleOrGet0(dataList.get(21).get(date)))
                .date(getDateFromString(date))
                .company(company)
                .build();
    }

    private double parseDoubleOrGet0(Object value)
    {
        if (value instanceof Double) return (double) value;

        try
        {
            return parseDouble((String) value);
        }catch (Exception e)
        {
            log.error("The double value {} can't be parsed", value);
            return 0.0;
        }
    }

}
