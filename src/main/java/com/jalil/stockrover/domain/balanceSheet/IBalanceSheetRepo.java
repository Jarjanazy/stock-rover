package com.jalil.stockrover.domain.balanceSheet;

import com.jalil.stockrover.common.dbprojection.MaxDateProjection;
import com.jalil.stockrover.domain.company.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBalanceSheetRepo extends CrudRepository<BalanceSheet, Integer>
{
   /* @Query("select new com.jalil.stockrover.domain.dbprojection.MaxDateProjection(max(b.date)) " +
            "from BalanceSheet b where b.company= ?1")
    Optional<MaxDateProjection> findByCompanyAndDateMax(Company company);*/
}
