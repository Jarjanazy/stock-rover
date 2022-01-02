package com.jalil.stockrover.common;

import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.jalil.stockrover.common.util.Utils.getDateFromString;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DynamicDataRepoTest
{
    @Autowired
    IBalanceSheetRepo balanceSheetRepo;

    @Autowired
    ICompanyRepo companyRepo;

    @Autowired
    DynamicDataRepo dynamicDataRepo;

    @Test
    public void givenTwoBalanceSheetInDB_WhenOneDateIsBiggest_ThenFetchIt()
    {
        Company company = Company
                .builder()
                .companyName("TEST")
                .companySymbol("test")
                .companyNameDisplay("test")
                .countryCode("test")
                .exchange("test")
                .industry("test")
                .build();
        companyRepo.save(company);

        LocalDateTime date1 = getDateFromString("2020-05-06");
        BalanceSheet balanceSheet1 = BalanceSheet.builder().company(company).date(date1).build();

        LocalDateTime date2 = getDateFromString("2020-07-06");
        BalanceSheet balanceSheet2 = BalanceSheet.builder().company(company).date(date2).build();

        balanceSheetRepo.saveAll(asList(balanceSheet1, balanceSheet2));

        Optional<LocalDateTime> byCompanyAndDateMax = dynamicDataRepo.findByCompanyAndDateMax(BalanceSheet.class, company);

        assertThat(byCompanyAndDateMax).isPresent();
        assertThat(byCompanyAndDateMax.get()).isEqualTo(date2);
    }

}
