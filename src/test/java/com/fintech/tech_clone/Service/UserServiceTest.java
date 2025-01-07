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
//          userr = new UserResponse();
//          userr.setUser_id(user.getUser_id());
//          userr.setRole_name(user.getRole().getRole_name());
//          userr.setUser_name(user.getUser_name());
//          userr.setUser_password(user.getUser_password());
//          userr.setUser_fullname(user.getUser_fullname());
//          userr.setUser_email(user.getUser_email());
//          userr.setUser_phone(user.getUser_phone());
//          userr.setUser_citizen_identification(user.getUser_citizen_identification());
//          userr.setUser_address(user.getUser_address());
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
//        Mockito.when(userRepository.findUserByUserName(request.getUser_name())).thenReturn(null);
          userSteps.validUsername(request,userRepository);
//        Mockito.when(roleRepository.findById(request.getRole_id()))
//                .thenReturn(Optional.of(role));
        userSteps.validRole(request,roleRepository);

//        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
//                .thenReturn(null);
        userSteps.validPhone(request,userRepository);
//        Mockito.when(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification()))
//                .thenReturn(null);
        userSteps.validCitizen(request,userRepository);
        User savedUser = new User();
        savedUser.setUser_name(request.getUser_name());
        savedUser.setUser_password("encoded_password");
        savedUser.setRole(role);
        Mockito.when(passwordEncoder.encode(request.getUser_password())).thenReturn("encoded password");
        Mockito.when(authentication.getName()).thenReturn("admin");
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(savedUser);
        User result = userSteps.createUser(userService,request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getUser_name(), result.getUser_name());
    }
    @Test
    void getAllUser_success() {
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
        org.springframework.data.domain.Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);
        Page<UserResponse> userResponsePage = userService.getAllUser(pageable);
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
        role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
                .thenReturn(new User());
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.updateUser(1,request1));
        Assertions.assertEquals("phone existed", exception.getMessage());
    }
    @Test
    void updateUser_roleNotExisted(){
//          role = new Role();
//          role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.empty());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.updateUser(1,request1));
        Assertions.assertEquals("Role not found", exception.getMessage());
    }
    @Test
    void updateUser_citizenExisted(){
        role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
                .thenReturn(null);
        Mockito.when(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification()))
                .thenReturn(new User());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.updateUser(1,request1));
        Assertions.assertEquals("CITIZEN IDENTIFICATION existed", exception.getMessage());
    }
    @Test
    void updateUser_onSuccess(){
        User existingUser = new User();
        existingUser.setUser_id(1);
        role = new Role();
        role.setRole_id(1);
        Mockito.when(userRepository.findUserById(1)).thenReturn(existingUser);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
                .thenReturn(null);
        Mockito.when(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification()))
                .thenReturn(null);
        Mockito.when(passwordEncoder.encode(request1.getUser_password())).thenReturn("encoded new password");
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(existingUser);
        User result = userService.updateUser(1,request1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(request1.getUser_fullname(), result.getUser_fullname());
    }
    @Test
    void createUser_userExisted(){
        role = new Role();
        role.setRole_id(1);
        Mockito.when(userRepository.findUserByUserName(request.getUser_name())).thenReturn(new User());
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.createUser(request));
        Assertions.assertEquals(ErrorCode.USER_EXISTED.getCode(), exception.getErrorCode().getCode());
    }
    @Test
    void createUser_userPhoneExisted(){
        role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
                .thenReturn(new User());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.createUser(request));
        Assertions.assertEquals("phone existed", exception.getMessage());
    }
    @Test
    void createUser_userCitizenIdentificationExisted(){
        role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
                .thenReturn(null);
        Mockito.when(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification()))
                .thenReturn(new User());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.createUser(request));
        Assertions.assertEquals("Citizen Identification existed!", exception.getMessage());
    }
    @Test
    void createUser_roleNotExisted(){
        role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.empty());
        AppException exception = Assertions.assertThrows(AppException.class,
                ()-> userService.createUser(request));
        Assertions.assertEquals("Invalid Role", exception.getMessage());
    }
    @Test
    void getUserById_fail(){
        Mockito.when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> userService.getUser(1));
        Assertions.assertEquals("User not found", exception.getMessage());
    }
    @Test
    void findAllUserByUserName_success(){
        ArrayList<User> arrayList = new ArrayList<>();
        arrayList.add(user);
        Mockito.when(userRepository.searchAllUserByUserName(ArgumentMatchers.anyString()))
                .thenReturn(arrayList);
        ArrayList<UserResponse> result = userService.findAllUserByUserName("admin");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(arrayList.get(0).getUser_fullname(), result.get(0).getUser_fullname());
    }
    @Test
    void findAllUserByUserName_returnNull(){
        Mockito.when(userRepository.searchAllUserByUserName(ArgumentMatchers.anyString()))
                .thenReturn(null);
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.findAllUserByUserName("admin"));
        Assertions.assertEquals(ErrorCode.USER_NOT_EXISTED.getCode(),exception.getErrorCode().getCode());
    }
    @Test
    void authenticate_UserNameNotExisted(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_name("testUsername");
        loginRequest.setUser_password("testPassword");
        Mockito.when(userRepository.findUserByUserName(ArgumentMatchers.anyString())).thenReturn(null);
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.authenticate(loginRequest));
        Assertions.assertEquals(ErrorCode.USER_NOT_EXISTED.getCode(),exception.getErrorCode().getCode());
    }
    @Test
    void authenticate_WrongPassword(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_name("testUsername");
        loginRequest.setUser_password("testPassword");
        Mockito.when(userRepository.findUserByUserName(ArgumentMatchers.anyString())).thenReturn(user);
        passwordEncoder =  Mockito.mock(BCryptPasswordEncoder.class);
        Mockito.when(passwordEncoder.matches(loginRequest.getUser_password(), user.getUser_password())).thenReturn(true);
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.authenticate(loginRequest));
        Assertions.assertEquals(ErrorCode.WRONG_PASSWORD.getCode(),exception.getErrorCode().getCode());
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
        Mockito.when(userRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(user));
        UserResponse result = userService.getUser(userId);
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
        user = new User();
        role = new Role();
        role.setRole_name("Admin");
        user.setUser_id(userId);
        user.setRole(role);
        user.setUser_name("john_doe");
        user.setUser_password("password123");
        user.setUser_fullname("John Doe");
        user.setUser_email("john.doe@example.com");
        user.setUser_phone("123456789");
        user.setUser_citizen_identification("1234567890");
        user.setUser_address("123 Main St");
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.activeUser(userId);
        Assertions.assertEquals("actived",result.getUser_status());
    }
    @Test
    void activeUser_onFail(){
        Mockito.when(userRepository.findUserById(1)).thenReturn(null);
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.activeUser(1));
        Assertions.assertEquals(exception.getMessage(),ErrorCode.USER_NOT_EXISTED.getMessage());
    }
    @Test
    void unactiveUser_onSuccess(){
        int userId = 1;
        user = new User();
        role = new Role();
        role.setRole_name("Admin");
        user.setUser_id(userId);
        user.setRole(role);
        user.setUser_name("john_doe");
        user.setUser_password("password123");
        user.setUser_fullname("John Doe");
        user.setUser_email("john.doe@example.com");
        user.setUser_phone("123456789");
        user.setUser_citizen_identification("1234567890");
        user.setUser_address("123 Main St");
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.unActiveUser(userId);
        Assertions.assertEquals("unactived",result.getUser_status());
    }
    @Test
    void unactiveUser_onFail(){
        Mockito.when(userRepository.findUserById(1)).thenReturn(null);
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.unActiveUser(1));
        Assertions.assertEquals(exception.getMessage(),ErrorCode.USER_NOT_EXISTED.getMessage());
    }
    @Test
    void deleteUser_onSuccess(){
        int userId = 1;
        user = new User();
        user.setUser_id(userId);
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User result = userService.deleteUser(userId);
        Assertions.assertEquals("deleted",result.getUser_status());
    }
    @Test
    void deleteUser_onFail(){
        Mockito.when(userRepository.findUserById(1)).thenReturn(null);
        AppException exception = Assertions.assertThrows(AppException.class,
                () -> userService.deleteUser(1));
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