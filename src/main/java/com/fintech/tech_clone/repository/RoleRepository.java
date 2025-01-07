package com.fintech.tech_clone.repository;
import com.fintech.tech_clone.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query(value = "SELECT * FROM tblrole WHERE role_name = :role_name", nativeQuery = true)
    Role findRole(@Param("role_name") String roleName);
}