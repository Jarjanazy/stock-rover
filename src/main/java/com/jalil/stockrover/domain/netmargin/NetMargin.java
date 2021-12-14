package com.jalil.stockrover.domain.netmargin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "net_margin")
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NetMargin
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="ttm_revenue")
    private double ttmRevenue;

    @Column(name="ttm_net_income")
    private double ttmNetIncome;

    @Column(name="net_margin_percentage")
    private double netMarginPercentage;

}
