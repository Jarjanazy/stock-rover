package com.jalil.stockrover.crawler.cashflowstatement;

import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.common.service.FilterService;
import com.jalil.stockrover.common.HtmlPageFetcher;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.common.service.convertor.SheetToEntityConvertor;
import com.jalil.stockrover.domain.cashflowstatement.CashFlowStatement;
import com.jalil.stockrover.domain.cashflowstatement.ICashFlowStatementRepo;
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
public class CashFlowStatementCrawlerServiceTest
{

    @Mock
    HtmlPageFetcher htmlPageFetcher;

    @Mock
    ICashFlowStatementRepo cashFlowStatementRepo;

    @Mock
    DynamicDataRepo dynamicDataRepo;

    @Mock
    ToDataStructureConvertor toDataStructureConvertor;

    @Mock
    SheetToEntityConvertor sheetToEntityConvertor;

    @Captor
    ArgumentCaptor<List<String>> datesArgumentCaptor;

    CashFlowStatementCrawlerService cashFlowStatementCrawlerService;

    @BeforeEach
    public void setup()
    {
        FilterService filterService = new FilterService(dynamicDataRepo, toDataStructureConvertor);
        cashFlowStatementCrawlerService = new CashFlowStatementCrawlerService(htmlPageFetcher, cashFlowStatementRepo, filterService, sheetToEntityConvertor, toDataStructureConvertor);
    }

    @Test
    public void givenDataAndDates_WhenOneDateIsSmallerThanLargestOneInDB_ThenDontAddIt() throws IOException
    {

        when(toDataStructureConvertor.getDatesFromData(any()))
                .thenReturn(asList("2020-01-05", "2020-02-05"));

        Company company = Company.builder().companySymbol("AAPL").companyName("apple").build();

        LocalDateTime maxDate = getDateFromString("2020-01-10");

        when(dynamicDataRepo.findByCompanyAndDateMax(CashFlowStatement.class, company))
                .thenReturn(Optional.of(maxDate));

        cashFlowStatementCrawlerService.crawlCashFlowStatement(company);

        verify(sheetToEntityConvertor).mapToCashFlowStatements(any(), datesArgumentCaptor.capture(), any());

        List<String> capturedDates = datesArgumentCaptor.getValue();
        assertThat(capturedDates).hasSize(1);
        assertThat(capturedDates.get(0)).isEqualTo("2020-02-05");
    }

}
