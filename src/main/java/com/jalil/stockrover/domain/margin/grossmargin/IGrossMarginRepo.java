package com.jalil.stockrover.domain.margin.grossmargin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrossMarginRepo extends CrudRepository<GrossMargin, Integer>
{
}
