package com.jalil.stockrover.domain.ratio.operatingMargin;

import com.jalil.stockrover.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operating_margin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OperatingMargin
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="ttm_revenue")
    private double ttmRevenue;

    @Column(name="ttm_operating_income")
    private double ttmOperatingIncome;

    @Column(name="operating_margin_percentage")
    private double operatingMarginPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
