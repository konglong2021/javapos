package com.pos.javapos.helper;

import com.pos.javapos.authentication.dto.CurrentUserDto;
import com.pos.javapos.authentication.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrentUserInfo {

   private Authentication init(){
       SecurityContext context = SecurityContextHolder.getContext();
       // Retrieve the Authentication object
       Authentication authentication = context.getAuthentication();
       return authentication;
   }

   public Boolean hasPermission(String permission){
       Authentication authentication = init();
       if (authentication != null && authentication.isAuthenticated()) {
           UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
           List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();
           return authorities.stream()
                   .anyMatch(authority -> authority.getAuthority().equals(permission));
       }
       return false;
   }

   public CurrentUserDto getCurrentUser(){
       Authentication authentication = init();
       if (authentication != null && authentication.isAuthenticated()) {
           UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
           CurrentUserDto currentUser = new CurrentUserDto();
           currentUser.setId(Long.parseLong(user.getId()));
           currentUser.setUsername(user.getUsername());
           currentUser.setRoles(user.getAuthorities());
           currentUser.setShop(user.getShop());
           currentUser.setBranch(user.getBranch());
           return currentUser;
       }
       return null;
   }
}
