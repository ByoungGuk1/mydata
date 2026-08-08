package com.app.mydata.domain.mydata.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @Builder
public class MydataFundDTO {

    private String fundCode;
    private String fundName;
    private Double foreignStockRatio;
    private LocalDate inceptionDate;
}
