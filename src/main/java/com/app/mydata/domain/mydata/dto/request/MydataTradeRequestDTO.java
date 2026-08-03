package com.app.mydata.domain.mydata.dto.request;

import lombok.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @Builder
public class MydataTradeRequestDTO {
    private String ciHash;
    private LocalDate fromDate;
    private LocalDate toDate;
}
