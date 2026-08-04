package com.app.mydata.domain.mydata.dto.response;

import com.app.mydata.domain.mydata.dto.MydataRiaAccountDTO;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @Builder
public class MydataRiaAccountResponseDTO {
    private Long mydataAccountId;
    private String ciHash;
    private String brokerName;
    private BigDecimal riaLimit;
    private BigDecimal riaCumulativeSell;

    public MydataRiaAccountResponseDTO(MydataRiaAccountDTO dto) {
        this.mydataAccountId = dto.getMydataAccountId() != null ? dto.getMydataAccountId() : null;
        this.ciHash = dto.getCiHash() != null ? dto.getCiHash() : null;
        this.brokerName = dto.getBrokerName() != null ? dto.getBrokerName() : null;
        this.riaLimit = dto.getRiaLimit() != null ? dto.getRiaLimit() : null;
        this.riaCumulativeSell = dto.getRiaCumulativeSell() != null ? dto.getRiaCumulativeSell() : null;
    }
}
