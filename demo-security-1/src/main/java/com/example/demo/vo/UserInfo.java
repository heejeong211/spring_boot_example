package com.example.demo.vo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id; //GenerationType.IDENTITY 사용하려면 db에 ai 추가..
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Column(length = 255, nullable = false, unique = true)
	private String account;
	
	@Column(length = 255, nullable = false)
	private String password;
	
	@Column(length = 255)
	private String auth;
	
	@Builder
	  public UserInfo(String name, String account, String password, String auth) {
	    this.name = name;
		this.account = account;
	    this.password = password;
	    this.auth = auth;
	  }

	//사용자의 권한을 콜렉션 형태로 반환
	//단, 클래스 자료형은 GrantedAuthority를 구현해야 함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
		for(String role : auth.split(",")) {
			roles.add(new SimpleGrantedAuthority(role));
		}
		return roles;
	}

	//사용자의 id를 반환 (unique한 값)
	@Override
	public String getUsername() {
		
		return account;
	}
	
	//사용자의 password를 반환
	 @Override
	  public String getPassword() {
	    
		 return password;
	  }

	 //계정 만료 여부 반환
	@Override
	public boolean isAccountNonExpired() {
		//만료되었는지 확인하는 로직
		return true; //true => 만료되지 않았음
	}

	//계정 잠금 여부 반환
	@Override
	public boolean isAccountNonLocked() {
		//계정 잠금되었는지 확인하는 로직
		return true; //true => 잠금되지 않았음
	}

	//패스워드의 만료 여부 반환
	@Override
	public boolean isCredentialsNonExpired() {
		//패스워드가 만료되었는지 확인하는 로직
		return true; //true => 만료되지 않았음
	}

	//계정 사용 가능 여부 반환
	@Override
	public boolean isEnabled() {
		//계정이 사용 가능한지 확인하는 로직
		return true; //true => 사용 가능
	}
	

}
