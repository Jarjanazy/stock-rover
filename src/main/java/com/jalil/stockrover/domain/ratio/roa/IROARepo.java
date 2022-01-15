package com.jalil.stockrover.domain.ratio.roa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IROARepo extends CrudRepository<ROA, Integer>
{
}
