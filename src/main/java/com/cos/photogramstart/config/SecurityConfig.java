package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //SecurityConfig 이 파일로 시큐리티를 활성화 시킨다.
@Configuration //IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //SecurityConfig가 IOC될 때 같이 등록됨
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super 삭제 -> 기존 시큐리티가 가지고 있는 기능이 모두 비활성화 됨
        //우리가 원하는대로 설정 셋팅
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") //Get 인증이 필요한 페이지에 접근 시 호출하는 페이지
                .loginProcessingUrl("/auth/signin") //Post로 이 주소가 요청되면 스프링 시큐리티가 요청을 낚아채서 로그인 대신 진행
                .defaultSuccessUrl("/");
    }
}
