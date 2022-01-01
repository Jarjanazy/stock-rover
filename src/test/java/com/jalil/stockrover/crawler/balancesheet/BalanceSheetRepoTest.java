package com.jalil.stockrover.crawler.balancesheet;

import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import com.jalil.stockrover.domain.company.ICompanyRepo;
import com.jalil.stockrover.domain.dbprojection.MaxDateProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.jalil.stockrover.common.util.Utils.getDateFromString;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BalanceSheetRepoTest
{
    @Autowired
    IBalanceSheetRepo balanceSheetRepo;

    @Autowired
    ICompanyRepo companyRepo;

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

        Optional<MaxDateProjection> byCompanyAndDateMax = balanceSheetRepo.findByCompanyAndDateMax(company);

        assertThat(byCompanyAndDateMax).isPresent();
        assertThat(byCompanyAndDateMax.get().getDate()).isEqualTo(date2);
    }

}
