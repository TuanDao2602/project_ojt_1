package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.entity.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users findByEmail(String email);
    @Query (value = "from Users u where u.userStatus=true ")
    List<Users> findAllUser();
    List<Users>searchByUserNameContainingOrUserId(String userName,int userId);
    List<Users>findUsersByAdress(String address);


}