package com.jalil.stockrover.domain.grossmargin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="gross_margin")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
public class GrossMargin
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="ttm_revenue")
    private double ttmRevenue;

    @Column(name="ttm_gross_profit")
    private double ttmGrossProfit;

    @Column(name="gross_margin_percentage")
    private double grossMarginPercentage;
}
