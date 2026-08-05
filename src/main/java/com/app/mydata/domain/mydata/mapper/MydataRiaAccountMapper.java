package com.app.mydata.domain.mydata.mapper;

import com.app.mydata.domain.mydata.dto.MydataRiaAccountDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MydataRiaAccountMapper {

    List<MydataRiaAccountDTO> selectByCiHash(@Param("ciHash") String ciHash);

    void insertAccount(MydataRiaAccountDTO accountDTO);
}