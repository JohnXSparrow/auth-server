package br.com.AuthServer.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.AuthServer.model.Privilege;
import br.com.AuthServer.model.Role;
import br.com.AuthServer.model.User;
import br.com.AuthServer.repository.UserRepository;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String email) {
  
        User user = userRepository.findByEmail(email);
        if (user == null) {
        	throw new UsernameNotFoundException(email);
        }
        
        if (!user.isEnabled()) {
        	throw new DisabledException("Account disabled");
        }
 
        return new org.springframework.security.core.userdetails.User(
        		user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
    }
 
    private Collection<? extends GrantedAuthority> getAuthorities(
      Collection<Role> roles) {  
        return getGrantedAuthorities(getPrivileges(roles));
    }
 
    private List<String> getPrivileges(Collection<Role> roles) {  
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
 
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
