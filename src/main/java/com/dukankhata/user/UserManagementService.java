package com.dukankhata.user;
import com.dukankhata.auth.User; import com.dukankhata.auth.UserRepository; import com.dukankhata.common.exception.*; import com.dukankhata.common.security.CurrentUserService; import com.dukankhata.staff.*; import com.dukankhata.user.dto.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service public class UserManagementService {
 private final UserRepository users; private final StaffRepository staff; private final CurrentUserService current; private final PasswordEncoder encoder;
 public UserManagementService(UserRepository u,StaffRepository s,CurrentUserService c,PasswordEncoder e){users=u;staff=s;current=c;encoder=e;}
 @Transactional(readOnly=true) public List<UserResponse> list(){return users.findByShopIdOrderByCreatedAtDesc(current.shopId()).stream().map(UserResponse::from).toList();}
 @Transactional public UserResponse createStaffUser(CreateStaffUserRequest r){
  Staff s=staff.findByIdAndShopId(r.staffId(),current.shopId()).orElseThrow(()->new ResourceNotFoundException("Staff not found"));
  if(!s.isActive()) throw new BusinessException("Cannot create login for inactive staff");
  if(users.existsByStaffId(s.getId())) throw new ConflictException("This staff member already has a login");
  String mobile=r.mobile().trim(); if(users.existsByMobile(mobile)) throw new ConflictException("Mobile number is already registered");
  User u=new User(); u.setName(s.getName()); u.setMobile(mobile); u.setPasswordHash(encoder.encode(r.password())); u.setRole("STAFF"); u.setActive(true); u.setShop(current.requireUser().getShop()); u.setStaff(s);
  return UserResponse.from(users.save(u));
 }
 @Transactional public UserResponse setActive(UUID id, boolean active){User u=users.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found")); if(!u.getShop().getId().equals(current.shopId())) throw new ResourceNotFoundException("User not found"); if("OWNER".equals(u.getRole())&&!active) throw new BusinessException("Owner account cannot be disabled"); u.setActive(active); return UserResponse.from(users.save(u));}
}
