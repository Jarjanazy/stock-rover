package com.jalil.stockrover.common.service;

import com.google.gson.internal.LinkedTreeMap;
import com.jalil.stockrover.common.repo.DynamicDataRepo;
import com.jalil.stockrover.common.service.convertor.ToDataStructureConvertor;
import com.jalil.stockrover.domain.company.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.jalil.stockrover.common.util.Utils.getDateFromString;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilterService
{
    private final DynamicDataRepo dynamicDataRepo;

    private final ToDataStructureConvertor toDataStructureConvertor;

    public List<String> filterDatesBiggerThanOnesInDB(List<LinkedTreeMap<String, String>> data, Company company, Class entityToCastTo)
    {
        List<String> dates = toDataStructureConvertor.getDatesFromData(data);

        Optional<LocalDateTime> maxDate = dynamicDataRepo.findByCompanyAndDateMax(entityToCastTo, company);

        if (maxDate.isEmpty()) return dates;

        return dates
                .stream()
                .filter(date -> dateIsBiggerThanMax(date, maxDate.get()))
                .collect(Collectors.toList());
    }

    private boolean dateIsBiggerThanMax(String date, LocalDateTime maxDate)
    {
        try
        {
            return getDateFromString(date).isAfter(maxDate);
        }catch (Exception e)
        {
            log.error("An error happened while comparing {} to maxDate", date, e);
            return false;
        }

    }
}
