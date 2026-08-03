package com.app.mydata.domain.mydata.mapper;

import com.app.mydata.domain.mydata.dto.MydataKeyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MydataKeyMapper {

    int existsByCiHash (@Param("ciHash") String ciHash);

    void insertKey(MydataKeyDTO keyDTO);
}
