package com.fortalezasec.firewarning.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.fortalezasec.firewarning.domain.Tipo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSec implements UserDetails {

  private static final long serialVersionUID = 1L;

  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSec(String username, String password, Set<Tipo> perfis) {
    super();
    this.username = username;
    this.password = password;
    this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
        .collect(Collectors.toSet());
  }

  public UserSec() {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public boolean hasRole(Tipo tipo) {
    return getAuthorities().contains(new SimpleGrantedAuthority(tipo.getDescricao()));
  }


}
