package com.app.mydata.domain.mydata.mapper;

import com.app.mydata.domain.mydata.dto.MydataTradeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MydataTradeMapper {
    List<MydataTradeDTO> selectByCiHashAndPeriod(
            @Param("ciHash") String ciHash,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    void insertTrade(MydataTradeDTO tradeDTO);
}
