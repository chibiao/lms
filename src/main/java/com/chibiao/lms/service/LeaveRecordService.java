package com.chibiao.lms.service;

import com.chibiao.lms.domain.LeaveRecord;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

/**
 * 请假单相关service
 *
 * @author : 迟彪
 * @date : 2020/5/7
 */
public interface LeaveRecordService {
    /**
     * 添加请假单
     *
     * @param leaveRecord 请假单信息
     * @return 是否添加成功
     */
    Boolean addLeaveRecord(LeaveRecord leaveRecord);

    /**
     * 更新请假单信息
     *
     * @param leaveRecord 请假单信息
     * @return 是否更新成功
     */
    Boolean updateLeaveRecord(LeaveRecord leaveRecord);

    /**
     * 删除请假单信息
     *
     * @param id 请假单id
     * @return 是否删除成功
     */
    Boolean deleteLeaveRecord(Long id);

    /**
     * 更新请假单状态
     *
     * @param id          请假单id
     * @param leaveStatus 请假单状态
     * @return 是否更新成功
     */
    Boolean updateLeaveStatus(Long id, Integer leaveStatus);

    /**
     * 查询请假单
     *
     * @param leaveRecord 请假单入参
     * @return 请假单list
     */
    PageListRes selectLeaveRecord(LeaveRecord leaveRecord, PageParam pageParam);
}
