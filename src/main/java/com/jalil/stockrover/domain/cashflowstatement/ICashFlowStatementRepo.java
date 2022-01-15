package com.jalil.stockrover.domain.cashflowstatement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICashFlowStatementRepo extends CrudRepository<CashFlowStatement, Integer>
{
}
