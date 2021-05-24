package com.wwm.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccount extends User{
	private static final long serialVersionUID = 1L;
	private static final String ROLE_PREFIX = "ROLE_";
	
	private Account account;
	
	public UserAccount(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public UserAccount(Account account) {
		super(account.getId(), account.getPassword(), authorities(account));
		this.account = account;
	}
	private static Collection<? extends GrantedAuthority> authorities(Account account){
		List<GrantedAuthority> authorities = new ArrayList();
		if(account.isAdmin()) {
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "ADMIN"));
		} else {
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "USER"));
		}
		return authorities;
	}
	@Override
	public boolean isEnabled() {
		if(this.account.getAccStatus() == null || !"U".equals(this.account.getAccStatus())) {
			return false;
		}else {
			return true;
		}
	}
}
