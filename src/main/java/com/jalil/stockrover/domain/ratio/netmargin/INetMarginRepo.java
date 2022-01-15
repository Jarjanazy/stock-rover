package com.jalil.stockrover.domain.ratio.netmargin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INetMarginRepo extends CrudRepository<NetMargin, Integer>
{
}
