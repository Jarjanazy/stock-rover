package com.jalil.stockrover.domain.dbprojection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MaxDateProjection
{
    private LocalDateTime date;
}
