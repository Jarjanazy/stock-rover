package com.jalil.stockrover.domain.cashflowstatement;

import com.jalil.stockrover.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_flow_statement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CashFlowStatement
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "net_income")
    private double netIncome;

    @Column(name = "total_depreciation_amortization")
    private double totalDepreciationAndAmortization;

    @Column(name = "other_non_cash_items")
    private double otherNonCashItems;

    @Column(name = "total_non_cash_items")
    private double totalNonCashItems;

    @Column(name = "change_in_accounts_receivable")
    private double changeInAccountsReceivable;

    @Column(name = "change_in_inventories")
    private double changeInInventories;

    @Column(name = "change_in_accounts_payable")
    private double changeInAccountsPayable;

    @Column(name = "change_in_assets_liabilities")
    private double changeInAssetsLiabilities;

    @Column(name = "total_change_in_assets_liabilities")
    private double totalChangeInAssetsLiabilities;

    @Column(name = "operating_activities_cash_flow")
    private double operatingActivitiesCashFlow;

    @Column(name = "property_plant_equipment_net_change")
    private double propertyPlantEquipmentNetChange;

    @Column(name = "intangible_assets_net_change")
    private double intangibleAssetsNetChange;

    @Column(name = "net_acquisitions_divestitures")
    private double netAcquisitionsDivestitures;

    @Column(name = "short_term_investments_net_change")
    private double shortTermInvestmentsNetChange;

    @Column(name = "long_term_investments_net_change")
    private double longTermInvestmentsNetChange;

    @Column(name = "other_investing_activities")
    private double otherInvestingActivities;

    @Column(name = "investing_activities_cash_flow")
    private double investingActivitiesCashFlow;

    @Column(name = "net_long_term_debt")
    private double netLongTermDebt;

    @Column(name = "net_current_debt")
    private double netCurrentDebt;

    @Column(name = "debt_issuance_retirement_net_total")
    private double debtIssuanceRetirementNetTotal;

    @Column(name = "net_common_equity_issued_repurchased")
    private double netCommonEquityIssuedRepurchased;

    @Column(name = "net_total_equity_issued_repurchased")
    private double netTotalEquityIssuedRepurchased;

    @Column(name = "total_common_and_preferred_stock_dividends_paid")
    private double totalCommonAndPreferredStockDividendsPaid;

    @Column(name = "other_financial_activities")
    private double otherFinancialActivities;

    @Column(name = "financial_activities_cash_flow")
    private double financialActivitiesCashFlow;

    @Column(name = "net_cash_flow")
    private double netCashFlow;
}
