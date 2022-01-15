package com.jalil.stockrover.domain.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepo extends CrudRepository<Company, Integer>
{
}
