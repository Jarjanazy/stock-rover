package com.jalil.stockrover.domain.ratio.roa;

import com.jalil.stockrover.domain.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roa")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ROA
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="ttm_net_income")
    private double ttmNetIncome;

    @Column(name="total_assets")
    private double totalAssets;

    @Column(name="return_on_assets_percentage")
    private double returnOnAssetsPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
