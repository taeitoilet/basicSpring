package com.fintech.tech_clone.Service;
import com.fintech.tech_clone.Step.UserServiceStep;
import com.fintech.tech_clone.dto.request.login.LoginRequest;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.dto.request.user.UserUpdateRequest;
import com.fintech.tech_clone.dto.response.LoginResponse;
import com.fintech.tech_clone.dto.response.UserResponse;
import com.fintech.tech_clone.entity.Permission;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.exception.AppException;
import com.fintech.tech_clone.exception.ErrorCode;
import com.fintech.tech_clone.repository.RoleRepository;
import com.fintech.tech_clone.repository.UserRepository;
import com.fintech.tech_clone.service.UserService;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@SpringBootTest
@ExtendWith(SerenityJUnit5Extension.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Steps
    UserServiceStep userSteps;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private Authentication authentication;
    private UserCreationRequest request;
    private UserUpdateRequest request1;
    private User user;
    private Role role;
    private UserResponse userr;
    @BeforeEach
    private void initData() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request = new UserCreationRequest();
        request.setRole_id(1);
        request.setUser_name("admin");
        request.setUser_password("admin");
        request.setUser_fullname("Minh Quy");
        request.setUser_email("taeitoilet@gmail.com");
        request.setUser_phone("0378932206");
        request.setUser_citizen_identification("031203012111");
        request.setUser_address("Hai Phong");
        request.setUser_money(10000);
        request1 = new UserUpdateRequest();
        request1.setRole_id(1);
        request1.setUser_password("admin");
        request1.setUser_fullname("Minh Quy");
        request1.setUser_email("taeitoilet@gmail.com");
        request1.setUser_phone("0378932206");
        request1.setUser_citizen_identification("031203012111");
        request1.setUser_address("Hai Phong");
        request1.setUser_money(10000);
        user = new User();
        role = new Role();
        role.setRole_id(1);
        role.setRole_name("Admin");
        user.setUser_id(1);
        user.setRole(role);
        user.setUser_name("admin");
        user.setUser_password("admin");
        user.setUser_fullname("Minh Quy");
        user.setUser_email("taeitoilet@gmail.com");
        user.setUser_phone("0378932206");
        user.setUser_citizen_identification("031203012111");
        user.setUser_address("Hai Phong");
        user.setUser_money(10000);
    }
    @Test
    void abc(){
        int a =1;
        int b = 10;
        org.assertj.core.api.Assertions.assertThat(a).isEqualTo(1);
    }
    @Test
    void createUser_success(){
        role = new Role();
        role.setRole_id(1);
        userSteps.validUsername(request.getUser_name(),userRepository);
        userSteps.validRole(request.getRole_id(),roleRepository);
        userSteps.validPhone(request.getUser_phone(),userRepository);
        userSteps.validCitizen(request.getUser_citizen_identification(),userRepository);
        userSteps.saveUser(user,userRepository);
        User result = userSteps.createUser(userService,request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getUser_name(), result.getUser_name());
    }
    @Test
    void getAllUser_success() {
        userSteps.mockFindAllUser(userRepository);
        Page<UserResponse> userResponsePage = userSteps.getAllUser(userService);
        Assertions.assertNotNull(userResponsePage);
        Assertions.assertEquals(2, userResponsePage.getContent().size());
        UserResponse userResponse1 = userResponsePage.getContent().get(0);
        Assertions.assertEquals("admin", userResponse1.getUser_name());
        Assertions.assertEquals("admin", userResponse1.getUser_fullname());
        Assertions.assertEquals("abc@xyz.com", userResponse1.getUser_email());
        Assertions.assertEquals("123456789", userResponse1.getUser_phone());
        Assertions.assertEquals("Admin", userResponse1.getRole().getRole_name());
        UserResponse userResponse2 = userResponsePage.getContent().get(1);
        Assertions.assertEquals("JaneDoe", userResponse2.getUser_name());
        Assertions.assertEquals("Jane Doe", userResponse2.getUser_fullname());
        Assertions.assertEquals("janedoe@example.com", userResponse2.getUser_email());
        Assertions.assertEquals("987654321", userResponse2.getUser_phone());
        Assertions.assertEquals("User", userResponse2.getRole().getRole_name());
    }
    @Test
    void updateUser_userPhoneExisted(){
        userSteps.existedUser(1,userRepository,user);
        userSteps.validRole(request1.getRole_id(),roleRepository);
        userSteps.existedPhone(request1.getUser_phone(),userRepository);
        RuntimeException exception = userSteps.updateUserFail(request1,userService);
        Assertions.assertEquals("phone existed", exception.getMessage());
    }
    @Test
    void updateUser_roleNotExisted(){
        userSteps.invalidRole(request1.getRole_id(),roleRepository);
        AppException exception = userSteps.updateUserFail(request1,userService);
        Assertions.assertEquals(ErrorCode.INVALID_ROLE.getMessage(), exception.getMessage());
    }
    @Test
    void updateUser_citizenExisted(){
        userSteps.validRole(request1.getRole_id(),roleRepository);
        userSteps.existedUser(1,userRepository,user);
        userSteps.validPhone(request1.getUser_phone(),userRepository);
        userSteps.existedCitizen(request1.getUser_citizen_identification(),userRepository);
        AppException exception = userSteps.updateUserFail(request1,userService);
        Assertions.assertEquals(ErrorCode.CITIZEN_IDENTIFICATION_EXIST.getMessage(), exception.getMessage());
    }
    @Test
    void updateUser_onSuccess(){
        User existingUser = new User();
        existingUser.setUser_id(1);
        userSteps.existedUser(1,userRepository,user);
        userSteps.validRole(request1.getRole_id(),roleRepository);
        userSteps.validPhone(request1.getUser_phone(),userRepository);
        userSteps.validCitizen(request1.getUser_citizen_identification(),userRepository);
        userSteps.saveUser(user,userRepository);
        User result = userSteps.updateUserSuccess(request1,userService);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request1.getUser_fullname(), result.getUser_fullname());

    }
    @Test
    void createUser_userExisted(){
        userSteps.validRole(1,roleRepository);
        userSteps.existedUsername(request.getUser_name(),userRepository);
        AppException exception = userSteps.createUserFail(userService,request);
        Assertions.assertEquals(ErrorCode.USER_EXISTED.getCode(), exception.getErrorCode().getCode());
    }
    @Test
    void createUser_userPhoneExisted(){
        userSteps.validRole(1,roleRepository);
        userSteps.existedPhone(request.getUser_phone(),userRepository);
        AppException exception = userSteps.createUserFail(userService,request);
        Assertions.assertEquals(ErrorCode.PHONE_EXIST.getMessage(), exception.getMessage());
    }
    @Test
    void createUser_userCitizenIdentificationExisted(){
        userSteps.validRole(1,roleRepository);
        userSteps.validPhone(request.getUser_phone(),userRepository);
        userSteps.existedCitizen(request.getUser_citizen_identification(),userRepository);
        AppException exception = userSteps.createUserFail(userService,request);
        Assertions.assertEquals(ErrorCode.CITIZEN_IDENTIFICATION_EXIST.getMessage(), exception.getMessage());
    }
    @Test
    void createUser_roleNotExisted(){
        userSteps.invalidRole(1,roleRepository);
        AppException exception = userSteps.createUserFail(userService,request);
        Assertions.assertEquals(ErrorCode.INVALID_ROLE.getMessage(), exception.getMessage());
    }
    @Test
    void getUserById_fail(){
        userSteps.invalidUserId(userRepository);
        AppException exception =userSteps.getUserByIdFail(1,userService);
        Assertions.assertEquals(ErrorCode.USER_NOT_EXISTED.getMessage(), exception.getMessage());
    }
    @Test
    void findAllUserByUserName_success(){
        userSteps.mockSearchUserByUserName(userRepository,user);
        ArrayList<UserResponse> result = userSteps.findAllUserByUserName(userService);
        Assertions.assertNotNull(result);
    }
    @Test
    void findAllUserByUserName_returnNull(){
        userSteps.mockSearchUserByUserNameNull(userRepository);
        AppException exception = userSteps.findAllUserByUserNameFail(userService);
        Assertions.assertEquals(ErrorCode.USER_NOT_EXISTED.getCode(),exception.getErrorCode().getCode());
    }
    @Test
    void authenticate_UserNameNotExisted(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_name("testUsername");
        loginRequest.setUser_password("testPassword");
        userSteps.validUsername(loginRequest.getUser_name(),userRepository);
        AppException exception = userSteps.loginFail(userService,loginRequest);
        Assertions.assertEquals(ErrorCode.USER_NOT_EXISTED.getCode(),exception.getErrorCode().getCode());
    }
    @Test
    void authenticate_WrongPassword(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_name("testUsername");
        loginRequest.setUser_password("testPassword");
        userSteps.existedUsername(loginRequest.getUser_name(),userRepository);
        userSteps.mockPasswordWrong(loginRequest.getUser_password(),user.getUser_password(),passwordEncoder);
        AppException exception = userSteps.loginFail(userService,loginRequest);
        Assertions.assertEquals(ErrorCode.WRONG_PASSWORD.getMessage(),exception.getMessage());
    }
    @Test
    void authenticate_onSuccess(){
        User existingUser = new User();
        existingUser.setUser_password("$2a$10$qFVjD2ZJ.jNHCWDGmkCa3.VNemDUWmOrmV9agFqYxb9e8A9yCluqC");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_name("admin");
        loginRequest.setUser_password("admin");
        Mockito.when(userRepository.findUserByUserName(ArgumentMatchers.anyString())).thenReturn(existingUser);
        Mockito.when(passwordEncoder.encode(loginRequest.getUser_password())).thenReturn("$2a$10$qFVjD2ZJ.jNHCWDGmkCa3.VNemDUWmOrmV9agFqYxb9e8A9yCluqC");
        Mockito.when(passwordEncoder.matches(loginRequest.getUser_password(), existingUser.getUser_password())).thenReturn(true);
        LoginResponse result = userService.authenticate(loginRequest);
        Assertions.assertEquals(true, result.isValidate());
    }
    @Test
    void getUserById_success(){
        user = new User();
        role = new Role();
        role.setRole_name("Admin");
        int userId = 1;
        user.setUser_id(userId);
        user.setRole(role);
        user.setUser_name("john_doe");
        user.setUser_password("password123");
        user.setUser_fullname("John Doe");
        user.setUser_email("john.doe@example.com");
        user.setUser_phone("123456789");
        user.setUser_citizen_identification("1234567890");
        user.setUser_address("123 Main St");

        userSteps.mockFinbyId(userRepository,user);
        UserResponse result = userSteps.getUserByIdSuccess(1,userService);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userId, result.getUser_id());
        Assertions.assertEquals("Admin", result.getRole().getRole_name());
        Assertions.assertEquals("john_doe", result.getUser_name());
        Assertions.assertEquals("John Doe", result.getUser_fullname());
        Assertions.assertEquals("john.doe@example.com", result.getUser_email());
        Assertions.assertEquals("123456789", result.getUser_phone());
        Assertions.assertEquals("1234567890", result.getUser_citizen_identification());
        Assertions.assertEquals("123 Main St", result.getUser_address());
    }
    @Test
    void activeUser_onSuccess(){
        int userId = 1;
        userSteps.existedUser(userId,userRepository,user);
        userSteps.saveUser(user,userRepository);
        User result = userSteps.activeUserSuccess(userService,userId);
        Assertions.assertEquals("actived",result.getUser_status());
    }
    @Test
    void activeUser_onFail(){
        userSteps.invalidUserId(userRepository);
        AppException exception = userSteps.activeUserFail(userService);
        Assertions.assertEquals(exception.getMessage(),ErrorCode.USER_NOT_EXISTED.getMessage());
    }
    @Test
    void unactiveUser_onSuccess(){
        int userId = 1;
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userSteps.existedUser(userId,userRepository,user);
        userSteps.saveUser(user,userRepository);
        User result = userSteps.unactiveUserSuccess(userService,userId);
        Assertions.assertEquals("unactived",result.getUser_status());
    }
    @Test
    void unactiveUser_onFail(){
        userSteps.invalidUserId(userRepository);
        AppException exception = userSteps.unactiveUserFail(userService);
        Assertions.assertEquals(exception.getMessage(),ErrorCode.USER_NOT_EXISTED.getMessage());
    }
    @Test
    void deleteUser_onSuccess(){
        int userId = 1;
        userSteps.existedUser(userId,userRepository,user);
        userSteps.saveUser(user,userRepository);
        User result = userSteps.deleteUserSuccess(userService,userId);
        Assertions.assertEquals("deleted",result.getUser_status());
    }
    @Test
    void deleteUser_onFail(){
        userSteps.invalidUserId(userRepository);
        AppException exception = userSteps.deleteUserFail(userService);
        Assertions.assertEquals(exception.getMessage(),ErrorCode.USER_NOT_EXISTED.getMessage());
    }
    @Test
    void buildScope_onSuccess() {
        // Given
        Role Role = Mockito.mock(Role.class);
        User User = Mockito.mock(User.class);
        Mockito.when(Role.getRole_name()).thenReturn("ADMIN");
        Mockito.when(Role.getPermissions()).thenReturn(Arrays.asList(
                new Permission("READ","",""),
                new Permission("WRITE","","")
        ));
        Mockito.when(User.getRole()).thenReturn(Role);
        // When
        String result = userService.buildScope(User);
        // Then
        Assertions.assertEquals("ROLE_ADMIN READ WRITE", result);
    }
}