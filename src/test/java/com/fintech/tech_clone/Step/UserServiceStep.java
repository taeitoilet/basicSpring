package com.fintech.tech_clone.Step;
import com.fintech.tech_clone.dto.request.login.LoginRequest;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.dto.request.user.UserUpdateRequest;
import com.fintech.tech_clone.dto.response.LoginResponse;
import com.fintech.tech_clone.dto.response.UserResponse;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.exception.AppException;
import com.fintech.tech_clone.repository.RoleRepository;
import com.fintech.tech_clone.repository.UserRepository;
import com.fintech.tech_clone.service.UserService;
import net.serenitybdd.annotations.Step;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
public class UserServiceStep {
    @Step("Check role hợp lệ ")
    public void validRole(int role_id,RoleRepository roleRepository){
        Role role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(role_id))
                .thenReturn(Optional.of(role));
    }
    @Step("Check username chưa tồn tại")
    public void validUsername(String username,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserName(username)).thenReturn(null);
    }
    @Step("Check số điện thoại chưa tồn tại")
    public void validPhone(String user_phone,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserPhone(user_phone))
                .thenReturn(null);
    }
    @Step("Check CCCD chưa tồn tại")
    public void validCitizen(String citizen,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserCitizenIdentification(citizen))
                .thenReturn(null);
    }
    @Step ("Mock so dien thoai da ton tai")
    public void existedPhone(String user_phone,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserPhone(user_phone))
                .thenReturn(new User());
    }
    @Step("Mock role khong ton tai")
    public void invalidRole(int role_id,RoleRepository roleRepository){
        Mockito.when(roleRepository.findById(role_id))
                .thenReturn(Optional.empty());
    }
    @Step("Mock cccd khong ton tai")
    public void existedCitizen(String citizen,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserCitizenIdentification(citizen))
                .thenReturn(new User());
    }
    @Step("Mock username da ton tai")
    public void existedUsername(String username,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserName(username)).thenReturn(new User());

    }
    @Step("Mock tim kiem theo user-id tra ve user")
    public void existedUser(int user_id,UserRepository userRepository, User user){
        Mockito.when(userRepository.findUserById(user_id)).thenReturn(user);
    }
    @Step("Mock tim kiem theo user-id tra ve null")
    public void invalidUserId(UserRepository userRepository){
        Mockito.when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
    }
    @Step("Mock save user")
    public void saveUser(User user, UserRepository userRepository){
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

    }
    @Step("Goi ham updateUser fail")
    public AppException updateUserFail(UserUpdateRequest request,UserService userService){
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.updateUser(1,request));
        return exception;
    }
    @Step("Goi ham updateUser success")
    public User updateUserSuccess(UserUpdateRequest request,UserService userService) {
        return userService.updateUser(1,request);
    }
    @Step("Mock du lieu list User")
    public List<User> mockListUser(){
        Role role1 = new Role();
        role1.setRole_id(1);
        role1.setRole_name("Admin");
        Role role2 = new Role();
        role2.setRole_id(2);
        role2.setRole_name("User");
        User user1 = new User();
        user1.setUser_id(1);
        user1.setUser_name("admin");
        user1.setUser_fullname("admin");
        user1.setUser_email("abc@xyz.com");
        user1.setUser_phone("123456789");
        user1.setRole(role1);
        User user2 = new User();
        user2.setUser_id(2);
        user2.setUser_name("JaneDoe");
        user2.setUser_fullname("Jane Doe");
        user2.setUser_email("janedoe@example.com");
        user2.setUser_phone("987654321");
        user2.setRole(role2);
        List<User> users = Arrays.asList(user1, user2);
        return users;
    }
    @Step("Mock ham findAll cua userRespository")
    public void mockFindAllUser(UserRepository userRepository){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(mockListUser(), pageable, mockListUser().size());
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);
    }
    @Step("Gọi hàm createUser success")
    public User createUser(UserService userService, UserCreationRequest request) {
        return userService.createUser(request);
    }
    @Step("Goi ham createUser fail")
    public AppException createUserFail(UserService userService, UserCreationRequest request){
        return Assertions.assertThrows(AppException.class,
                () -> userService.createUser(request));
    }
    @Step("Goi ham getAllUser")
    public Page<UserResponse> getAllUser(UserService userService){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 10);
        return userService.getAllUser(pageable);
    }
    @Step("Goi ham getUserById fail")
    public AppException getUserByIdFail(int user_id, UserService userService){
        return  Assertions.assertThrows(AppException.class,
                () -> userService.getUser(user_id));
    }
    @Step("Mock du lieu tra ve cua ham searchUserByUserName not null")
    public void mockSearchUserByUserName(UserRepository userRepository, User user){
        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(user);
        Mockito.when(userRepository.searchAllUserByUserName(ArgumentMatchers.anyString()))
                .thenReturn(arrayList);
    }
    @Step("Mock du lieu tra ve cua ham searchUserByUserName null")
    public void mockSearchUserByUserNameNull(UserRepository userRepository){
        Mockito.when(userRepository.searchAllUserByUserName(ArgumentMatchers.anyString()))
                .thenReturn(null);
    }
    @Step("Goi ham findAllUserByUserName success")
    public ArrayList<UserResponse> findAllUserByUserName(UserService userService){
        return userService.findAllUserByUserName("admin");
    }
    @Step("Goi ham findAllUserByUserName fail")
    public AppException findAllUserByUserNameFail(UserService userService){
        return Assertions.assertThrows(AppException.class,
                () -> userService.findAllUserByUserName("admin"));
    }
    @Step("Goi ham login fail")
    public AppException loginFail(UserService userService, LoginRequest loginRequest){
        return Assertions.assertThrows(AppException.class,
                () -> userService.authenticate(loginRequest));
    }
    @Step("Mock password sai")
    public void mockPasswordWrong(String loginPassword, String user_password, PasswordEncoder passwordEncoder){
        Mockito.when(passwordEncoder.matches(loginPassword, user_password)).thenReturn(false);
    }
    @Step("Ma hoa password")
    public void mockEncodePassword(String password,PasswordEncoder passwordEncoder){
        Mockito.when(passwordEncoder.encode(password)).thenReturn("$2a$10$qFVjD2ZJ.jNHCWDGmkCa3.VNemDUWmOrmV9agFqYxb9e8A9yCluqC");
    }
    @Step("Mock password dung")
    public void validPassword(String password,String user_password, PasswordEncoder passwordEncoder){
        Mockito.when(passwordEncoder.encode(password)).thenReturn("$2a$10$qFVjD2ZJ.jNHCWDGmkCa3.VNemDUWmOrmV9agFqYxb9e8A9yCluqC");
        Mockito.when(passwordEncoder.matches(password, user_password)).thenReturn(true);
    }
    @Step("Goi ham login success")
    public LoginResponse loginSuccess(LoginRequest loginRequest,UserService userService){
        return userService.authenticate(loginRequest);
    }
    @Step("Goi ham getUserById")
    public UserResponse getUserByIdSuccess(int user_id,UserService userService){
        return userService.getUser(user_id);
    }
    @Step("Mock findById cua userResponse")
    public void mockFinbyId(UserRepository userRepository,User user){
        Mockito.when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(user));

    }
    @Step("Goi ham activeUser success")
    public User activeUserSuccess(UserService userService,int user_id){
        return userService.activeUser(user_id);
    }
    @Step("Goi ham activeUser fail")
    public AppException activeUserFail(UserService userService){
        return  Assertions.assertThrows(AppException.class,
                () -> userService.activeUser(1));
    }
    @Step("Goi ham unactiveUser success")
    public User unactiveUserSuccess(UserService userService,int user_id){
        return userService.unActiveUser(user_id);
    }
    @Step("Goi ham unactiveUser fail")
    public AppException unactiveUserFail(UserService userService){
        return  Assertions.assertThrows(AppException.class,
                () -> userService.unActiveUser(1));
    }
    @Step("Goi ham deleteUser success")
    public User deleteUserSuccess(UserService userService,int user_id){
        return userService.deleteUser(user_id);
    }
    @Step("Goi ham deleteUser fail")
    public AppException deleteUserFail(UserService userService){
        return  Assertions.assertThrows(AppException.class,
                () -> userService.deleteUser(1));
    }
}