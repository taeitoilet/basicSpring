package com.fintech.tech_clone.controller;
import com.fintech.tech_clone.dto.request.user.UserCreationRequest;
import com.fintech.tech_clone.dto.request.user.UserDeleteRequest;
import com.fintech.tech_clone.dto.request.user.UserUpdateRequest;
import com.fintech.tech_clone.dto.response.ApiResponse;
import com.fintech.tech_clone.dto.response.UserResponse;
import com.fintech.tech_clone.entity.User;
import com.fintech.tech_clone.exception.AppException;
import com.fintech.tech_clone.exception.ErrorCode;
import com.fintech.tech_clone.service.UserService;
import com.fintech.tech_clone.validGroup.CreateGroup;
import com.fintech.tech_clone.validGroup.UpdateGroup;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PreAuthorize("hasAuthority('create_data')")
    @PostMapping("/create")
    ApiResponse<User> createUser(@RequestBody @Validated(CreateGroup.class) UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse ;
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping()
    ApiResponse<Page<UserResponse>> getAllUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        ApiResponse<Page<UserResponse>> apiResponse = new ApiResponse<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> user = userService.getAllUser(pageable);
        apiResponse.setResult(user);
//        System.out.println(userService.getAllUser().toString());
        return apiResponse;
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/{user_id}")
    ApiResponse<UserResponse> getUser(@PathVariable("user_id") int user_id){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUser(user_id));
        return apiResponse ;
    }
    @PreAuthorize("hasAuthority('update_data')")
    @PutMapping("/update/{user_id}")
    ApiResponse<User> updateUser(@PathVariable("user_id") int user_id, @RequestBody @Validated(UpdateGroup.class) UserUpdateRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(user_id  ,request));
//        userService.updateUserV2(user_id,request);
//        apiResponse.setMessage("Sua thanh cong");
        return apiResponse;
    }
    @PreAuthorize("hasAuthority('delete_data')")
    @PutMapping("/delete/{user_id}")
    ApiResponse<User> deleteUser(@PathVariable("user_id") int user_id){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.deleteUser(user_id));
//        userService.deleteUserV2(user_id);
//        apiResponse.setMessage("Xoa thanh cong");
        return apiResponse;
    }
    @PutMapping("/active/{user_id}")
    ApiResponse<User> activeUser(@PathVariable("user_id") int user_id){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.activeUser(user_id));
        return apiResponse;
    }
    @PutMapping("/unactive/{user_id}")
    ApiResponse<User> unactiveUser(@PathVariable("user_id") int user_id){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.unActiveUser(user_id));
        return apiResponse;
    }
    @PreAuthorize("hasAuthority('get_data')")
    @GetMapping("/find")
    ApiResponse<ArrayList<UserResponse>> findUserByUserName(@RequestParam String username){
        ApiResponse<ArrayList<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.findAllUserByUserName(username));
        return apiResponse;
    }
}
