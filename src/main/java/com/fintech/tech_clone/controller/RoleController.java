package com.fintech.tech_clone.controller;
import com.fintech.tech_clone.dto.response.ApiResponse;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public ApiResponse<List<Role>> getAll(){
        ApiResponse<List<Role>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.getAllRoles());
        return apiResponse ;
    }
}