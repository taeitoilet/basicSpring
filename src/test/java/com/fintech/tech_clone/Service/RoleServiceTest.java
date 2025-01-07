package com.fintech.tech_clone.Service;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.repository.RoleRepository;
import com.fintech.tech_clone.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.List;
@SpringBootTest
class RoleServiceTest {
    @Autowired
    private RoleService roleService;
    @MockBean
    private RoleRepository roleRepository;
    @Test
    void getAllRole_onSuccess(){
        Role role1, role2 ;
        role1 = new Role();
        role1.setRole_id(1);
        role1.setRole_name("admin");
        role2 = new Role();
        List<Role> list = Arrays.asList(role1,role2);
        Mockito.when(roleRepository.findAll()).thenReturn(list);
        List<Role> result = roleService.getAllRoles();
        Assertions.assertEquals("admin",result.get(0).getRole_name());
    }
}