package com.app.mydata.domain.mydata.dto.response;

import com.app.mydata.domain.mydata.dto.MydataTradeDTO;
import com.app.mydata.domain.mydata.type.StockType;
import com.app.mydata.domain.mydata.type.TradeType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString @Builder
public class MydataTradeResponseDTO {
    private Long tradeId;
    private String ciHash;
    private String brokerName;
    private TradeType tradeType;
    private StockType stockType;
    private BigDecimal qty;
    private LocalDate tradeDate;
    private BigDecimal amount;

    public MydataTradeResponseDTO(MydataTradeDTO dto) {
        this.tradeId = dto.getTradeId() != null ? dto.getTradeId() : null;
        this.ciHash = dto.getCiHash() != null ? dto.getCiHash() : null;
        this.brokerName = dto.getBrokerName() != null ? dto.getBrokerName() : null;
        this.tradeType = dto.getTradeType() != null ? dto.getTradeType() : null;
        this.stockType = dto.getStockType() != null ? dto.getStockType() : null;
        this.qty = dto.getQty() != null ? dto.getQty() : null;
        this.tradeDate = dto.getTradeDate() != null ? dto.getTradeDate() : null;
        this.amount = dto.getAmount() != null ? dto.getAmount() : null;
    }
}
