package com.billing.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billing.demo.model.dao.UserDao;
import com.billing.demo.model.entity.Role;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.billing.demo.model.entity.User user = userDao.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User doesn't exist " + username);
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		if(authorities.isEmpty()) {
			throw new UsernameNotFoundException("User doesn't exist " + username);
		}
		
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}

	
}
