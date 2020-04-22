package com.chibiao.lms.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;


@Configuration
public class DruidConfig {
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    //1.配置servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("loginUsername","admin");
        hashMap.put("loginPassword","admin");
        hashMap.put("allow","");//允许访问所有
        bean.setInitParameters(hashMap);
        return bean;
    }

    //2.配置Filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(hashMap);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
