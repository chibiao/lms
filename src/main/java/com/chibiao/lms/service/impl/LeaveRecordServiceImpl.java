package com.chibiao.lms.service.impl;

import com.chibiao.lms.domain.LeaveRecord;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.mapper.LeaveRecordMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.LeaveRecordService;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 请假单相关service
 *
 * @author : 迟彪
 * @date : 2020/5/7
 */
@Service
public class LeaveRecordServiceImpl implements LeaveRecordService {
    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Override
    public Boolean addLeaveRecord(LeaveRecord leaveRecord) {
        LeaveRecord record = leaveRecordMapper.selectLeaveRecordByStudentIdAndLeaveBeginTime(leaveRecord.getStudentId(),leaveRecord.getLeaveBeginTime(),leaveRecord.getLeaveEndTime(),null);
        if (record!=null){
            throw new BusinessException(BusinessErrorCode.LEAVERECORD_IS_EXIST);
        }
        leaveRecordMapper.insert(leaveRecord);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateLeaveRecord(LeaveRecord leaveRecord) {
        LeaveRecord record = leaveRecordMapper.selectLeaveRecordByStudentIdAndLeaveBeginTime(leaveRecord.getStudentId(),leaveRecord.getLeaveBeginTime(),leaveRecord.getLeaveEndTime(),leaveRecord.getId());
        if (record!=null){
            throw new BusinessException(BusinessErrorCode.LEAVERECORD_IS_EXIST);
        }
        leaveRecordMapper.updateByPrimaryKey(leaveRecord);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteLeaveRecord(Long id) {
        leaveRecordMapper.deleteByPrimaryKey(id);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateLeaveStatus(Long id, Integer leaveStatus) {
        leaveRecordMapper.updateLeaveStatus(id,leaveStatus);
        return Boolean.TRUE;
    }

    @Override
    public PageListRes selectLeaveRecord(LeaveRecord leaveRecord, PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<LeaveRecord> list = leaveRecordMapper.selectLeaveRecord(leaveRecord);
        return PageListResUtil.buildSuccessPageListRes(page,list);
    }
}
