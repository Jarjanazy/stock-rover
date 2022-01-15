package com.jalil.stockrover.domain.ratio.operatingMargin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOperatingMarginRepo extends CrudRepository<OperatingMargin, Integer>
{
}
