package com.fintech.tech_clone.service;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role>  getAllRoles(){
        return roleRepository.findAll();
    }
//    public Role getRoleByName(String role_name){
//        return roleRepository.findRole(role_name);
//    }
}
