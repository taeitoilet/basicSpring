package com.fintech.tech_clone.controller;
import com.fintech.tech_clone.dto.request.login.LoginRequest;
import com.fintech.tech_clone.dto.response.ApiResponse;
import com.fintech.tech_clone.dto.response.LoginResponse;
import com.fintech.tech_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping()
    public ApiResponse<LoginResponse> authenticate(@RequestBody LoginRequest request){
        var result = userService.authenticate(request);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setValidate(result.isValidate());
        loginResponse.setToken(result.getToken());
        apiResponse.setResult(loginResponse);
        return apiResponse;
    }
}