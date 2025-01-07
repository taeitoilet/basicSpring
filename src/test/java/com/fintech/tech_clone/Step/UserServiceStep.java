package com.fintech.tech_clone.Step;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.repository.RoleRepository;
import com.fintech.tech_clone.repository.UserRepository;
import com.fintech.tech_clone.service.UserService;
import net.serenitybdd.annotations.Step;
import org.mockito.Mockito;
import java.util.Optional;
public class UserServiceStep {
    @Step("Check role hợp lệ ")
    public void validRole(UserCreationRequest request,RoleRepository roleRepository){
        Role role = new Role();
        role.setRole_id(1);
        Mockito.when(roleRepository.findById(request.getRole_id()))
                .thenReturn(Optional.of(role));
    }
    @Step("Check username chưa tồn tại")
    public void validUsername(UserCreationRequest request,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserName(request.getUser_name())).thenReturn(null);
    }
    @Step("Check số điện thoại chưa tồn tại")
    public void validPhone(UserCreationRequest request,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserPhone(request.getUser_phone()))
                .thenReturn(null);
    }
    @Step("Check CCCD chưa tồn tại")
    public void validCitizen(UserCreationRequest request,UserRepository userRepository){
        Mockito.when(userRepository.findUserByUserCitizenIdentification(request.getUser_citizen_identification()))
                .thenReturn(null);
    }
    @Step("Gọi hàm tạo người dùng")
    public User createUser(UserService userService, UserCreationRequest request) {
        return userService.createUser(request);
    }
}