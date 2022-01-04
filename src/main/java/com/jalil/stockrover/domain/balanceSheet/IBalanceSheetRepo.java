package com.jalil.stockrover.domain.balanceSheet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBalanceSheetRepo extends CrudRepository<BalanceSheet, Integer>
{
}
