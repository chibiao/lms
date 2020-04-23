package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.LeaveRecord;
import java.util.List;

public interface LeaveRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LeaveRecord record);

    LeaveRecord selectByPrimaryKey(Long id);

    List<LeaveRecord> selectAll();

    int updateByPrimaryKey(LeaveRecord record);
}