package com.app.mydata.domain.mydata.dto.request;

import com.app.mydata.domain.mydata.dto.MydataRiaAccountDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiaAccountRequestDTO {
  @NotBlank
  private String ciHash;
  @NotBlank
  private String brokerName;
  @Positive
  @NotNull
  private BigDecimal riaLimit;
  @PositiveOrZero
  private BigDecimal riaCumulativeSell;

  public MydataRiaAccountDTO toDTO(){
    return MydataRiaAccountDTO.builder()
        .ciHash(this.getCiHash())
        .brokerName(this.getBrokerName())
        .riaLimit(this.getRiaLimit())
        .riaCumulativeSell(this.getRiaCumulativeSell() == null ? BigDecimal.ZERO : this.getRiaCumulativeSell())
        .build();
  }
}
