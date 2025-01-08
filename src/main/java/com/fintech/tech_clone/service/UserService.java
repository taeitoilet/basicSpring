package com.fintech.tech_clone.service;
import com.fintech.tech_clone.dto.request.login.LoginRequest;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.dto.request.user.UserDeleteRequest;
import com.fintech.tech_clone.dto.request.user.UserUpdateRequest;
import com.fintech.tech_clone.dto.response.LoginResponse;
import com.fintech.tech_clone.dto.response.UserResponse;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.exception.AppException;
import com.fintech.tech_clone.exception.ErrorCode;
import com.fintech.tech_clone.repository.RoleRepository;
import com.fintech.tech_clone.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
@Service
@Slf4j
public class UserService {
    @NonFinal
    @Value("${jwt.singerKey}")
    protected String SINGED_KEY = "lhLFCRHLCxBOwkZfwekYrzYzSE+CLCy5Mb1v7DLv2lMzOKaHR+c0b8nesilWrSPL";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    public User createUser(UserCreationRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Role role = roleRepository.findById(request.getRole_id()) .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));
        User user = new User();
        if(userRepository.findUserByUserName(request.getUser_name())!=null ){
            throw  new AppException(ErrorCode.USER_EXISTED);
        }
        else if(userRepository.findUserByUserPhone(request.getUser_phone())!=null ){
            throw  new AppException(ErrorCode.PHONE_EXIST);
        }
        else if(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification())!=null){
            throw  new AppException(ErrorCode.CITIZEN_IDENTIFICATION_EXIST);
        }
        else{
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            user.setUser_name(request.getUser_name());
            user.setUser_creater(authentication.getName());
            user.setUser_status("unactived");
            user.setUser_password(passwordEncoder.encode(request.getUser_password()));
            user.setRole(role);
            user.setUser_fullname(request.getUser_fullname());
            user.setUser_phone(request.getUser_phone());
            user.setUser_email(request.getUser_email());
            user.setUser_citizen_identification(request.getUser_citizen_identification());
            user.setUser_address(request.getUser_address());
            user.setUser_dob(request.getUser_dob());
            user.setUser_money(request.getUser_money());
            user.setUser_created_date(LocalDate.now());
            return userRepository.save(user);
        }
    }
    public UserResponse getUser(int id){
        User user =userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userr = new UserResponse();
        userr.setUser_id(user.getUser_id());
        userr.setRole(user.getRole());
        userr.setUser_name(user.getUser_name());
        userr.setUser_password(user.getUser_password());
        userr.setUser_fullname(user.getUser_fullname());
        userr.setUser_email(user.getUser_email());
        userr.setUser_phone(user.getUser_phone());
        userr.setUser_citizen_identification(user.getUser_citizen_identification());
        userr.setUser_address(user.getUser_address());
        userr.setUser_dob(user.getUser_dob());
        return userr;
    }
    public Page<UserResponse> getAllUser(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);
        Page<UserResponse> userResponsePage = userPage.map(user -> {
            UserResponse response = new UserResponse();
            response.setUser_id(user.getUser_id());
            response.setUser_name(user.getUser_name());
            response.setUser_id(user.getUser_id());
            response.setRole(user.getRole());
            response.setUser_password(user.getUser_password());
            response.setUser_fullname(user.getUser_fullname());
            response.setUser_email(user.getUser_email());
            response.setUser_phone(user.getUser_phone());
            response.setUser_citizen_identification(user.getUser_citizen_identification());
            response.setUser_address(user.getUser_address());
            response.setUser_dob(user.getUser_dob());
            response.setUser_status(user.getUser_status());
            return response;
        });
        return userResponsePage;
    }
    public User updateUser(int id, UserUpdateRequest request){
        Role role = roleRepository.findById(request.getRole_id()) .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = userRepository.findUserById(id);
        if(userRepository.findUserByUserPhone(request.getUser_phone()) != null){
            throw  new AppException(ErrorCode.PHONE_EXIST);
        }
        else if(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification())!= null){
            throw  new AppException(ErrorCode.CITIZEN_IDENTIFICATION_EXIST);
        }else {
            user.setUser_password(passwordEncoder.encode(request.getUser_password()));
            user.setRole(role);
            user.setUser_fullname(request.getUser_fullname());
            user.setUser_phone(request.getUser_phone());
            user.setUser_email(request.getUser_email());
            user.setUser_citizen_identification(request.getUser_citizen_identification());
            user.setUser_address(request.getUser_address());
            user.setUser_dob(request.getUser_dob());
            user.setUser_money(request.getUser_money());
            user.setUser_modified_date(LocalDate.now());
            return userRepository.save(user);
        }
    }
    //    public void updateUserV2(int id,UserUpdateRequest request){
//        if(userRepository.findUserByUserPhone(request.getUser_phone())!=null ){
//            throw  new RuntimeException("Userphone existed!");
//        }
//        else if(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification())!=null){
//            throw  new RuntimeException("Citizen Identification existed!");
//        }else{
//            userRepository.updateUser(id,request.getRole_id(), request.getUser_password(),
//                    request.getUser_fullname(), request.getUser_email(), request.getUser_phone(), request.getUser_address(),
//                    request.getUser_citizen_identification(), request.getUser_dob(), request.getUser_money(),
//                    LocalDate.now());
//        }
//
//    }
    public User deleteUser(int id){
        User user = userRepository.findUserById(id);
        if(userRepository.findUserById(id) != null){
            user.setUser_status("deleted");
            return userRepository.save(user);
        }
        else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }
    //    public void deleteUserV2(int id){
//        userRepository.DeleteUser(id);
//    }
    public User activeUser(int id){
        User user = userRepository.findUserById(id);
        if(userRepository.findUserById(id) != null){
            user.setUser_status("actived");
            return userRepository.save(user);
        }
        else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }
    public User unActiveUser(int id){
        User user = userRepository.findUserById(id);
        if(userRepository.findUserById(id) != null){
            user.setUser_status("unactived");
            return userRepository.save(user);
        }
        else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }
    public ArrayList<UserResponse> findAllUserByUserName(String username){
        ArrayList<User> user = userRepository.searchAllUserByUserName(username);
        ArrayList<UserResponse> userr = new ArrayList<>();
        if(user != null && user.size() > 0){
            for (User item : user
            ) {
                UserResponse u = new UserResponse();
                u.setUser_id(item.getUser_id());
                u.setRole(item.getRole());
                u.setUser_name(item.getUser_name());
                u.setUser_password(item.getUser_password());
                u.setUser_fullname(item.getUser_fullname());
                u.setUser_email(item.getUser_email());
                u.setUser_phone(item.getUser_phone());
                u.setUser_citizen_identification(item.getUser_citizen_identification());
                u.setUser_address(item.getUser_address());
                u.setUser_dob(item.getUser_dob());
                u.setUser_status(item.getUser_status());
                userr.add(u);
            }
            return userr;
        }
        else {
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }
    public LoginResponse authenticate(LoginRequest request){
        User user  = userRepository.findUserByUserName(request.getUser_name());
        if(user == null  ){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean authenticated = passwordEncoder.matches(request.getUser_password(),user.getUser_password());
            if(!authenticated){
                throw new AppException(ErrorCode.WRONG_PASSWORD);
            }
            var token = generateToken(user);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setValidate(true);
            return loginResponse;
        }
    }
    public String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUser_name())
                .issuer("abc")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SINGED_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    public String buildScope(User user){
        Role role = user.getRole();
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (role != null){
            stringJoiner.add("ROLE_" + role.getRole_name());
            role.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermission_name()));
        }
        return stringJoiner.toString();
    }

    public void deleteUserIfExpried(){
        List<User> users = userRepository.findDeletedUser();
        ArrayList<User> userDeletedList = new ArrayList<User>();
        long expiry = 180;
        for(User user : users){
            if(expiry <= ChronoUnit.DAYS.between(user.getUser_modified_date(),LocalDate.now())){
                userDeletedList.add(user);
            }
        }
        if(userDeletedList.size()>0){
            System.out.println("-------------Deleting User----------------");
            userRepository.deleteAll(userDeletedList);
        }else {
            System.out.println("-------------No User to Delete----------------");
        }
    }
    public List<User> getUserDeleted(){
        return userRepository.findDeletedUser();
    }
}