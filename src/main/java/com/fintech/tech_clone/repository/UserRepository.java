package com.fintech.tech_clone.repository;
import com.fintech.tech_clone.entity.Role;
import com.fintech.tech_clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer> {
    @Modifying
    @Query(value = "INSERT INTO tbluser (role_id, user_name, user_password, user_fullname, user_email, user_phone, user_address, user_citizen_identification, user_dob, user_money, user_created_date) " +
            "VALUES (:role_id, :user_name, :user_password, :user_fullname, :user_email, :user_phone, :user_address, :user_citizen_identification, :user_dob, :user_money, :user_created_date)", nativeQuery = true)
    void addUser(@Param("role_id") int roleId,
                 @Param("user_name") String userName,
                 @Param("user_password") String userPassword,
                 @Param("user_fullname") String userFullName,
                 @Param("user_email") String userEmail,
                 @Param("user_phone") String userPhone,
                 @Param("user_address") String userAddress,
                 @Param("user_citizen_identification") String userCitizenIdentification,
                 @Param("user_dob") Date userDob,
                 @Param("user_money") int userMoney,
                 @Param("user_created_date") LocalDate userCreatedDate
    );
    @Modifying
    @Query(value = "UPDATE tbluser SET role_id = :role_id,  user_password = :user_password, user_fullname = :user_fullname, user_email = :user_email, user_phone = :user_phone, user_address = :user_address, user_citizen_identification = :user_citizen_identification, user_dob = :user_dob, user_money = :user_money, user_modified_date = :user_modified_date " +
            "WHERE user_id = :user_id", nativeQuery = true)
    void updateUser(@Param("user_id") int userId,
                    @Param("role_id") int roleId,
                    @Param("user_password") String userPassword,
                    @Param("user_fullname") String userFullName,
                    @Param("user_email") String userEmail,
                    @Param("user_phone") String userPhone,
                    @Param("user_address") String userAddress,
                    @Param("user_citizen_identification") String userCitizenIdentification,
                    @Param("user_dob") Date userDob,
                    @Param("user_money") int userMoney,
                    @Param("user_modified_date") LocalDate userModifiedDate);
    @Modifying
    @Query(value = "UPDATE tbluser SET user_is_deleted = 1 WHERE user_id = :user_id", nativeQuery = true)
    void DeleteUser(@Param("user_id") int userId);
    @Query(value = "SELECT * FROM tbluser WHERE user_id = :user_id", nativeQuery = true)
    User findUserById(@Param("user_id") int userId);
    @Query(value = "SELECT * FROM tbluser WHERE user_name = :user_name", nativeQuery = true)
    User findUserByUserName(@Param("user_name") String userName);
    @Query(value = "SELECT * FROM tbluser WHERE user_name LIKE %:username%", nativeQuery = true)
    ArrayList<User> searchAllUserByUserName(@Param("username") String username);
    @Query(value = "SELECT * FROM tbluser WHERE user_phone = :user_phone", nativeQuery = true)
    User findUserByUserPhone(@Param("user_phone") String userPhone);
    @Query(value = "SELECT * FROM tblrole WHERE role_id = :role_id", nativeQuery = true)
    Role findRole(@Param("role_id") int roleId);
    @Query(value = "SELECT * FROM tbluser WHERE user_citizen_identification = :user_citizen_identification", nativeQuery = true)
    User findUserByUserCitizenIdentification(@Param("user_citizen_identification") String user_citizen_identification);
    @Query(value = "SELECT * FROM tbluser;" , nativeQuery = true)
    ArrayList<User> findAllUser();
    @Query(value = "SELECT * FROM tbluser WHERE user_status = 'deleted'", nativeQuery = true)
    List<User> findDeletedUser();
}