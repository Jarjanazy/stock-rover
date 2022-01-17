create table company
(
    id integer not null,
    company_name varchar(255) not null,
    company_name_display varchar(255) not null,
    company_symbol varchar(255) not null,
    country_code varchar(255) not null,
    exchange varchar(255) not null,
    industry varchar(255) not null,
    primary key (id)
);

create table balance_sheet
(
    id                            integer not null,
    cash_on_hand                  double,
    common_stock_net              double,
    comprehensive_income          double,
    date                          timestamp,
    inventory                     double,
    long_term_debt                double,
    long_term_investments         double,
    other_current_assets          double,
    other_long_term_assets        double,
    other_non_current_liabilities double,
    pre_paid_expenses             double,
    property_plant_equipment      double,
    receivables                   double,
    retained_earnings             double,
    share_holder_equity           double,
    total_assets                  double,
    total_current_assets          double,
    total_current_liabilities     double,
    total_liabilities             double,
    total_long_term_assets        double,
    total_long_term_liabilities   double,
    company_id                    integer,

    primary key (id)
    CONSTRAINT fk_company
    FOREIGN KEY (company_id)
    REFERENCES company(id)
);

create table cash_flow_statement
(
    id integer not null,
    change_in_accounts_payable double,
    change_in_accounts_receivable double,
    change_in_assets_liabilities double,
    change_in_inventories double,
    date timestamp,
    debt_issuance_retirement_net_total double,
    financial_activities_cash_flow double,
    intangible_assets_net_change double,
    investing_activities_cash_flow double,
    long_term_investments_net_change double,
    net_acquisitions_divestitures double,
    net_cash_flow double,
    net_common_equity_issued_repurchased double,
    net_current_debt double,
    net_income double,
    net_long_term_debt double,
    net_total_equity_issued_repurchased double,
    operating_activities_cash_flow double,
    other_financial_activities double,
    other_investing_activities double,
    other_non_cash_items double,
    property_plant_equipment_net_change double,
    short_term_investments_net_change double,
    total_change_in_assets_liabilities double,
    total_common_and_preferred_stock_dividends_paid double,
    total_depreciation_amortization double,
    total_non_cash_items double,
    company_id integer,

    primary key (id)
    CONSTRAINT fk_company
    FOREIGN KEY(company_id)
    REFERENCES company(id)
);

create table gross_margin (
                              id integer not null,
                              date timestamp,
                              gross_margin_percentage float,
                              ttm_gross_profit float,
                              ttm_revenue float,
                              company_id integer,
                              primary key (id),

                              CONSTRAINT fk_company
                                  FOREIGN KEY(company_id)
                                      REFERENCES company(id)
);

create table income_statement (
                                  id integer not null,
                                  basic_eps float,
                                  basic_shares_outstanding float,
                                  continuous_operations_income float,
                                  cost_of_goods_sold float,
                                  date timestamp,
                                  ebit float,
                                  ebitda float,
                                  eps float,
                                  gross_profit float,
                                  income_after_taxes float,
                                  income_taxes float,
                                  net_income float,
                                  operating_expenses float,
                                  operating_income float,
                                  pre_tax_income float,
                                  research_development_expenses float,
                                  revenue float,
                                  sga_expenses float,
                                  shares_outstanding float,
                                  total_operating_expenses float,
                                  company_id integer,

                                  primary key (id),
                                  CONSTRAINT fk_company
                                      FOREIGN KEY(company_id)
                                          REFERENCES company(id)
);

create table net_margin (
                            id integer not null,
                            date timestamp,
                            net_margin_percentage float,
                            ttm_net_income float,
                            ttm_revenue float,
                            company_id integer,

                            primary key (id),
                            CONSTRAINT fk_company
                                FOREIGN KEY(company_id)
                                    REFERENCES company(id)
);

create table operating_margin (
                                  id integer not null,
                                  date timestamp,
                                  operating_margin_percentage float,
                                  ttm_operating_income float,
                                  ttm_revenue float,
                                  company_id integer,
                                  primary key (id),
                                  CONSTRAINT fk_company
                                      FOREIGN KEY(company_id)
                                          REFERENCES company(id)

);

create table roa (
                     id integer not null,
                     date timestamp,
                     return_on_assets_percentage float,
                     total_assets float,
                     ttm_net_income float,
                     company_id integer,
                     primary key (id),
                     CONSTRAINT fk_company
                         FOREIGN KEY(company_id)
                             REFERENCES company(id)

);
