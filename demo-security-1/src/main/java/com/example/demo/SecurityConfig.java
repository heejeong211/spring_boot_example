package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration //해당 클래스를 Configuration으로 등록한다.
@EnableWebSecurity //Spring Security를 활성화 시킨다.
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private UserService userService; //로그인 요청 시, 입력된 유저 정보와 DB의 회원정보를 비교해 인증된 사용자인지 체크하는 로직이 정의되어 있다.
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //비밀번호 복호화/암호화하는 로직이 담긴 객체를 Bean으로 등록한다.
	}
	
	//WebSecurity는 FilterChainProxy를 생성하는 필터이다. 다양한 Filter 설정을 적용할 수 있다.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**"); //설정을 통해 Spring Security에서 해당 요청은 인증 대상에서 제외신다.
	}
	
	//HttpSecurity를 통해 HTTP 요청에 대한 보안을 설정할 수 있다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/member/**").hasAnyRole("USER, ADMIN")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/**").permitAll(); //http 요청에 대해서 모든 사용자가 /** 경로로 요청할 수 있지만, /member/**, /admin/** 경로는 인증된 사용자만 요청이 가능하다.
		
		http.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/")
		.permitAll(); //로그인 설정을 진행한다.
		
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login")
		.invalidateHttpSession(true); //로그아웃 설정을 진행한다.(로그아웃 성공시 세션 제거)
		
		http.exceptionHandling()
		.accessDeniedPage("/denied"); //권한이 없는 사용자가 접근했을 경우 이동할 경로를 지정한다.
	}
	
	//AuthenticationManager를 생성한다. AuthenticationManager는 사용자 인증을 담당한다.
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); //auth.userDetailsService(service)에 org.springframework.security.core.userdetails.UserDetailsService 인터페이스를 구현한 Service를 넘겨야 한다.
	}
	
}
