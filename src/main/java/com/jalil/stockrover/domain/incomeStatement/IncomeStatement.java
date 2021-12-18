package com.jalil.stockrover.domain.incomeStatement;

import com.jalil.stockrover.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "income_statement")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class IncomeStatement
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "cost_of_goods_sold")
    private double costOfGoodsSold;

    @Column(name = "gross_profit")
    private double grossProfit;

    @Column(name = "research_development_expenses")
    private double researchAndDevelopmentExpenses;

    @Column(name = "sga_expenses")
    private double sgaExpenses;

    @Column(name = "operating_expenses")
    private double operatingExpenses;

    @Column(name = "operating_income")
    private double operatingIncome;

    @Column(name = "total_operating_expenses")
    private double totalNonOperatingExpenses;

    @Column(name = "pre_tax_income")
    private double preTaxIncome;

    @Column(name = "income_taxes")
    private double incomeTaxes;

    @Column(name = "income_after_taxes")
    private double incomeAfterTaxes;

    @Column(name = "continuous_operations_income")
    private double continuousOperationsIncome;

    @Column(name = "net_income")
    private double netIncome;

    @Column(name = "ebitda")
    private double ebitda;

    @Column(name = "ebit")
    private double ebit;

    @Column(name = "basic_shares_outstanding")
    private double basicSharesOutstanding;

    @Column(name = "shares_outstanding")
    private double sharesOutstanding;

    @Column(name = "basic_eps")
    private double basicEps;

    @Column(name = "eps")
    private double eps;

}
