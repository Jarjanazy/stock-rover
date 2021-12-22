package com.jalil.stockrover.domain.balanceSheet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_sheet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BalanceSheet
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "cash_on_hand")
    private double cashOnHand;

    @Column(name = "receivables")
    private double receivables;

    @Column(name = "inventory")
    private double inventory;

    @Column(name = "pre_paid_expenses")
    private double prePaidExpenses;

    @Column(name = "other_current_assets")
    private double otherCurrentAssets;

    @Column(name = "total_current_assets")
    private double totalCurrentAssets;

    @Column(name = "property_plant_equipment")
    private double propertyPlantEquipment;

    @Column(name = "long_term_investments")
    private double longTermInvestments;

    @Column(name = "other_long_term_assets")
    private double otherLongTermAssets;

    @Column(name = "total_long_term_assets")
    private double totalLongTermAssets;

    @Column(name = "total_assets")
    private double totalAssets;

    @Column(name = "total_current_liabilities")
    private double totalCurrentLiabilities;

    @Column(name = "long_term_debt")
    private double longTermDebt;

    @Column(name = "other_non_current_liabilities")
    private double otherNonCurrentLiabilities;

    @Column(name = "total_long_term_liabilities")
    private double totalLongTermLiabilities;

    @Column(name = "total_liabilities")
    private double totalLiabilities;

    @Column(name = "common_stock_net")
    private double commonStockNet;

    @Column(name = "retained_earnings")
    private double retainedEarnings;

    @Column(name = "comprehensive_income")
    private double comprehensiveIncome;

    @Column(name = "share_holder_equity")
    private double shareHolderEquity;
}
