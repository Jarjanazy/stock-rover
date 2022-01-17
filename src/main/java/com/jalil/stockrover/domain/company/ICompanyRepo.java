package com.jalil.stockrover.domain.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompanyRepo extends CrudRepository<Company, Integer>
{
    List<Company> findAllByCompanySymbolIn(List<String> companiesSymbols);
}
