package ra.model.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.dto.respon.UserDTO;
import ra.model.entity.Users;

import java.util.List;

public interface UserService {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);
    Users findUserById(int userId);
    List<Users>findAllUser();
    Users findByEmail(String email);
    List<UserDTO>searchByUserNameOrUserId(String userName, int userId);
    List<Users>sortByUserName(String userName);
    List<Users>sortByNameAndId(String sendirecName,String sendirecId);
    Page<Users>getPagging(Pageable pageable);
//    List<Users>filter(String filter,String name);
    List<Users>filterbyAdress(String address);



}
