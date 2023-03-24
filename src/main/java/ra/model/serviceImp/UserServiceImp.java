package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.dto.respon.UserDTO;
import ra.model.entity.Users;
import ra.model.repository.UserRepository;
import ra.model.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Users findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Users saveOrUpdate(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users findUserById(int userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public List<Users> findAllUser() {
        return userRepository.findAllUser();
    }
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> searchByUserNameOrUserId(String userName, int userId) {
        List<Users> usersList= userRepository.searchByUserNameContainingOrUserId(userName,userId);
        List<UserDTO> userDTOList =new ArrayList<>();
        for (Users user:usersList) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUserName(user.getUserName());
            userDTO.setUserStatus(user.isUserStatus());
            userDTO.setAdress(user.getAdress());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhone(user.getPhone());
            userDTOList.add(userDTO);


        }
        return userDTOList;
    }

    @Override
    public List<Users> sortByUserName(String userName) {
        if (userName.equals("asc")){
            return userRepository.findAll(Sort.by("userName").ascending());
        }else {
            return userRepository.findAll(Sort.by("userName").descending());
        }

    }

    @Override
    public List<Users> sortByNameAndId(String sendirecName, String sendirecId) {
        if (sendirecName.equals("asc")){
            if (sendirecId.equals("asc")){
                return userRepository.findAll(Sort.by("userName").and(Sort.by("userId")));
            }else {
                return userRepository.findAll(Sort.by("userName").and(Sort.by("userId").descending()));
            }
        }else {
            if (sendirecId.equals("asc")){
                return userRepository.findAll(Sort.by("userName").descending().and(Sort.by("userId").ascending()));
            }else {
                return userRepository.findAll(Sort.by("userName").descending().and(Sort.by("userId").descending()));
            }
        }

    }

    @Override
    public Page<Users> getPagging(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<Users> filterbyAdress( String address) {

            return userRepository.findUsersByAdress(address);
        }
    }



