package com.jalil.stockrover.crawler.balancesheet;

import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.crawler.HtmlPageFetcher;
import com.jalil.stockrover.crawler.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.crawler.convertor.ToEntityConvertor;
import com.jalil.stockrover.domain.balanceSheet.BalanceSheet;
import com.jalil.stockrover.domain.balanceSheet.IBalanceSheetRepo;
import com.jalil.stockrover.domain.company.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.jalil.stockrover.common.util.Utils.getDateFromString;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceSheetCrawlerServiceTest
{
    @Mock
    HtmlPageFetcher htmlPageFetcher;

    @Mock
    IBalanceSheetRepo balanceSheetRepo;

    @Mock
    DynamicDataRepo dynamicDataRepo;

    @Mock
    ToDataStructureConvertor toDataStructureConvertor;

    @Mock
    ToEntityConvertor toEntityConvertor;

    @Captor
    ArgumentCaptor<List<String>> datesArgumentCaptor;

    BalanceSheetCrawlerService balanceSheetCrawlerService;

    @BeforeEach
    public void setup()
    {
        balanceSheetCrawlerService = new BalanceSheetCrawlerService(htmlPageFetcher, balanceSheetRepo, dynamicDataRepo, toEntityConvertor, toDataStructureConvertor);
    }

    @Test
    public void givenDataAndDates_WhenOneDateIsSmallerThanLargestOneInDB_ThenDontAddIt() throws IOException
    {

        when(toDataStructureConvertor.getDatesFromData(any()))
                .thenReturn(asList("2020-01-05", "2020-02-05"));

        Company company = Company.builder().companySymbol("AAPL").companyName("apple").build();

        LocalDateTime maxDate = getDateFromString("2020-01-10");

        when(dynamicDataRepo.findByCompanyAndDateMax(BalanceSheet.class, company))
                .thenReturn(Optional.of(maxDate));

        balanceSheetCrawlerService.crawlBalanceSheet(company);

        verify(toEntityConvertor).mapToBalanceSheets(any(), datesArgumentCaptor.capture(), any());

        List<String> capturedDates = datesArgumentCaptor.getValue();
        assertThat(capturedDates).hasSize(1);
        assertThat(capturedDates.get(0)).isEqualTo("2020-02-05");
    }

}
