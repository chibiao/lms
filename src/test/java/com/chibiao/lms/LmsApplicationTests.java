package com.chibiao.lms;

import com.chibiao.lms.domain.Clazz;
import com.chibiao.lms.domain.Department;
import com.chibiao.lms.domain.Specialty;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzService;
import com.chibiao.lms.service.DepartmentService;
import com.chibiao.lms.service.SpecialtyService;
import com.chibiao.lms.util.JsonUtil;
import com.chibiao.lms.util.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LmsApplicationTests {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisClient redisClient;
    @Test
    public void addDepartmentTest() {
        Department department = new Department();
        department.setDeptNo(123L);
        department.setDeptName("计算机科学与技术系");
        department.setDeptDesc("这是一个非常完美的院系");
        Boolean flag = departmentService.addDepartment(department);
        System.out.println(flag);
    }
    @Test
    public void deleteDepartmentTest(){
        Boolean flag = departmentService.deleteDepartment(123L);
        System.out.println(flag);
    }

    @Test
    public void addSpecialty(){
        Specialty specialty = new Specialty();
        specialty.setSpecialtyNo(123L);
        specialty.setSpecialtyName("计算机科学与技术专业");
        specialty.setSpecialtyDesc("最好的专业");
        Department department = new Department();
        department.setDeptNo(123L);
        specialty.setDepartment(department);
        Boolean aBoolean = specialtyService.addSpecialty(specialty);
        System.out.println(aBoolean);
    }
    @Test
    public void redisTest(){
        //boolean set = redisClient.set("chibiao", "试试行不行");
        //System.out.println(set);
        //Object chibiao = redisClient.get("chibiao");
        //System.out.println(chibiao);
        //boolean set = redisClient.set("chibiao1", "试试到底行不行");
        //System.out.println(set);
        stringRedisTemplate.opsForValue().set("chibiao3", "试试什么情况");
    }

    @Test
    public void addClazzTest(){
        Clazz clazz = new Clazz();
        Department department = new Department();
        department.setDeptNo(123L);
        Specialty specialty = new Specialty();
        specialty.setSpecialtyNo(123L);
        clazz.setClazzNo(123L);
        clazz.setClazzName("1班");
        clazz.setClazzYear(2016);
        clazz.setDepartment(department);
        clazz.setSpecialty(specialty);
        Boolean result = clazzService.addClazz(clazz);
        System.out.println(result);
    }
    @Test
    public void queryClazz(){
        PageParam pageParam = new PageParam();
        pageParam.setLimit(10);
        pageParam.setPage(1);
        PageListRes pageListRes = clazzService.queryClazz(pageParam);
        System.out.println(JsonUtil.toJSONString(pageListRes.getData()));
    }




}
