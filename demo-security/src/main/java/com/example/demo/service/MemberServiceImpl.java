package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.vo.Member;
import com.example.demo.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		Optional<Member> memberEntityWrapper = memberRepository.findByAccount(account);
		Member memberEntity = memberEntityWrapper.orElse(null); //Optional.orElse() 내부객체가 null이든 null이 아니든 무조건 실행됨.
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if(("admin").equals(account)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
		}
		
		return new User(memberEntity.getAccount(), memberEntity.getPassword(), authorities); 
	}

	@Override
	public Integer save(MemberVO memberVO) {
		Member member = memberVO.toEntity();
		
		//비밀번호 암호화
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member).getId();
	}
}

//loadUserByUsername 메소드는 입력한 account를 이용해 회원을 조회한다.
//그리고 회원 정보와 권한 정보가 담긴 User 클래스를 반환한다. (User 클래스는 UserDetails 인터페이스를 구현하고 있다.)

//비밀번호 인증은 Spring Security의 AuthenticationProvider 객체에서 진행한다.
//직접 커스텀해서 비밀번호 인증 로직을 구현할 수 있지만, 이 코드는 기본적으로 지원하는 AuthenticationProvide를 사용하겠다.