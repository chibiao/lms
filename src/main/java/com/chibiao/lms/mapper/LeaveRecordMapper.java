package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.LeaveRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LeaveRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LeaveRecord record);

    LeaveRecord selectByPrimaryKey(Long id);

    List<LeaveRecord> selectAll();

    int updateByPrimaryKey(LeaveRecord record);

    /**
     * 根据条件查询请假单
     * @param leaveRecord 请假单入参
     * @return 请假单list
     */
    List<LeaveRecord> selectLeaveRecord(LeaveRecord leaveRecord);

    /**
     * 更新请假单状态
     * @param id 请假单id
     * @param leaveStatus 请假单状态
     */
    void updateLeaveStatus(@Param("id") Long id,@Param("leaveStatus") Integer leaveStatus);

    /**
     * 查询请假记录是否冲突
     * @param studentId 学生id
     * @param leaveBeginTime 请假时间
     * @param leaveEndTime
     * @return
     */
    LeaveRecord selectLeaveRecordByStudentIdAndLeaveBeginTime(@Param("studentId") Long studentId, @Param("leaveBeginTime") Date leaveBeginTime,@Param("leaveEndTime") Date leaveEndTime);
}