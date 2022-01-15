package com.jalil.stockrover.domain.incomeStatement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIncomeStatementRepo extends CrudRepository<IncomeStatement, Integer>
{
}
