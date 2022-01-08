package com.jalil.stockrover.common.repo;

import com.jalil.stockrover.domain.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class DynamicDataRepo
{
    private final EntityManager entityManager;

    public Optional<LocalDateTime> findByCompanyAndDateMax(Class wantedEntity, Company company)
    {
        String query = format("select (max(e.date)) from %s e where e.company.id= %s", wantedEntity.getName(), company.getId());

        try {
            return Optional.ofNullable(entityManager
                    .createQuery(query, LocalDateTime.class)
                    .getSingleResult());
        } catch (NoResultException e)
        {
            return Optional.empty();
        }
    }

    public List<Company> findUnCrawledCompanies(Class wantedClass)
    {
        String query = format("select company from %s company where company.id not in (select distinct c.company.id from %s c)",
                Company.class.getName(), wantedClass.getName());

        return entityManager
                .createQuery(query, Company.class)
                .getResultList();
    }
}
