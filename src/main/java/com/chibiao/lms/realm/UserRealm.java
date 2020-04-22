package com.chibiao.lms.realm;

import com.chibiao.lms.authc.UserNamePasswordLoginTypeToken;
import com.chibiao.lms.domain.Admin;
import com.chibiao.lms.domain.Student;
import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.enums.LoginTypeEnum;
import com.chibiao.lms.service.AdminService;
import com.chibiao.lms.service.StudentService;
import com.chibiao.lms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserNamePasswordLoginTypeToken token = (UserNamePasswordLoginTypeToken) authenticationToken;
        String username = (String) token.getPrincipal();
        log.info("UserRealm -> doGetAuthenticationInfo start ###来到了认证###, authenticationToken={}", authenticationToken);
        if (LoginTypeEnum.STUDENT.getType().equals(token.getLoginType())) {
            Student student = studentService.selectByStudentId(Long.parseLong(username));
            if (student == null) {
                return null;
            }
            /*参数 主体,正确的密码,盐,当前realm的名称*/
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(student,
                    student.getStudentPassword(),
                    ByteSource.Util.bytes(student.getStudentName()),
                    this.getName());
            return info;
        }
        if (LoginTypeEnum.TEACHER.getType().equals(token.getLoginType())) {
            Teacher teacher = teacherService.selectByTeacherNo(Long.parseLong(username));
            if (teacher == null) {
                return null;
            }
            /*参数 主体,正确的密码,盐,当前realm的名称*/
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(teacher,
                    teacher.getTeacherPassword(),
                    ByteSource.Util.bytes(teacher.getTeacherName()),
                    this.getName());
            return info;
        }
        if (LoginTypeEnum.ADMIN.getType().equals(token.getLoginType())) {
            Admin admin = adminService.selectByAdminAccount(username);
            if (admin == null) {
                return null;
            }
            /*参数 主体,正确的密码,盐,当前realm的名称*/
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin,
                    admin.getAdminPassword(),
                    ByteSource.Util.bytes(admin.getAdminAccount()),
                    this.getName());
            return info;
        }
        return null;
    }
}
