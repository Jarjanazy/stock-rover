package com.jalil.stockrover.domain.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Company
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, name = "company_symbol")
    private String companySymbol;

    @Column(nullable = false, name = "company_name_display")
    private String companyNameDisplay;

    @Column(nullable = false, name = "exchange")
    private String exchange;

    @Column(nullable = false, name = "country_code")
    private String countryCode;

    @Column(nullable = false, name = "industry")
    private String industry;

}
