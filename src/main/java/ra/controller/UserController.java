package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.jwt.JwtTokenProvider;
import ra.model.dto.respon.ChangePassword;
import ra.model.dto.respon.UserDTO;
import ra.model.entity.ERole;
import ra.model.entity.Roles;
import ra.model.entity.Users;
import ra.model.service.RoleService;
import ra.model.service.UserService;
import ra.model.dto.request.LoginRequest;
import ra.model.dto.request.SigupRequest;
import ra.model.dto.respon.JwtResponse;
import ra.model.dto.respon.MessageResponse;
import ra.sercurity.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ra.model.entity.ERole.ROLE_ADMIN;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")


public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    //------------------------------------------SignUp--------------------------------------------------
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SigupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Usermame is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassWord(encoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        user.setAdress(signupRequest.getAdress());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setFullName(user.getFirstName() + user.getLastName());
        user.setAvartar(signupRequest.getAvartar());

        user.setCreated(LocalDateTime.now());
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
//---------------------------------------SignIn-------------------------------------------------------
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Users users = userService.findByUserName(loginRequest.getUserName());
        if (users.isUserStatus()){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
        //Sinh JWT tra ve client
            String jwt = tokenProvider.generateToken(customUserDetail);
            //Lay cac quyen cua user
            List<String> listRoles = customUserDetail.getAuthorities().stream()
                    .map(item ->item.getAuthority()).collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,customUserDetail.getUserId(),customUserDetail.getUsername(),customUserDetail.getFirstName(),customUserDetail.getLastName(),
                   customUserDetail.getAdress(),customUserDetail.getEmail(),
                    customUserDetail.getPhone(),customUserDetail.isUserStatus(),listRoles));

        }else {
            return ResponseEntity.ok("tài khoản của bạn đã bị khóa");
    }

    }
    //-----------------------logOut-----------------------------------
    @GetMapping("/logOut")
    public ResponseEntity<?> logOut(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        // Clear the authentication from server-side (in this case, Spring Security)
        /// Clear the authentication from server-side (in this case, Spring Security)
        /// Clear the authentication from server-side (in this case, Spring Security)''
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("You have been logged out.");
    }

    //------------------ResetPass-------------------------------------
    @PostMapping("resetPassword")

//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassWord(@RequestBody ChangePassword changePassword){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.findUserById(customUserDetails.getUserId());
        boolean check = encoder.matches(changePassword.getOldPassword(),users.getPassWord());
        //Đổi mật khẩu
        if (check){
            if (changePassword.getNewPassword().equals(changePassword.getComfimlNewPass())){
                users.setPassWord(encoder.encode(changePassword.getNewPassword()));
                userService.saveOrUpdate(users);

                return ResponseEntity.ok("Đổi mật khẩu thành công!");
            }else {
                return ResponseEntity.ok("Nhập lại mật khẩu không chính xác!");
            }
        }else {
            return ResponseEntity.ok("Mật khẩu cũ không chính xác!");
        }

    }

    //------------------------------getAlll---------------------------------------
    @GetMapping()
//    @PreAuthorize(" hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    //    @PreAuthorize(" hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")


    @PreAuthorize(" hasRole('ADMIN')")
    public List<Users> getAllUsers(){
        return userService.findAllUser();
    }
    @GetMapping("/{userId}")
    public Users getUserById(@PathVariable ("userId")int userId){
        return  userService.findUserById(userId);
    }

    //----------------------------Delete--------------------------------------
    @DeleteMapping("/deleteuser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId")int userId ){

        Users users= userService.findUserById(userId);
        if (users.getListRoles().size()==2){
            return ResponseEntity.ok("Không thể xóa tài khoản này");
        }
        else {
            users.setUserStatus(!users.isUserStatus());
//            System.out.println("status"+ users.isUserStatus());
            //            System.out.println("status"+ users.isUserStatus());

            userService.saveOrUpdate(users);
            return ResponseEntity.ok(("Đã khóa tài khoản thành công"));

        }
    }
    //----------------------------SearchByNameOrId---------------------------------

    @GetMapping("/search")
    public List<UserDTO>serachByNameOrId(@RequestParam("userName")String userName, @RequestParam("userId")int userId ){
        return userService.searchByUserNameOrUserId(userName,userId);
    }

    @GetMapping("/sortByName")
        public ResponseEntity<List<Users>> sortByUserName(@RequestParam("userName")String userName){
        List<Users> usersList =userService.sortByUserName(userName);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
        }
    @GetMapping("/sortByNameAndId")
    public ResponseEntity<List<Users>>sortByNameAndId(@RequestParam("sendirecName")String sendirecName,@RequestParam("sendirecId")String sendirecId){
        List<Users> usersList =userService.sortByNameAndId(sendirecName,sendirecId);
        return  new ResponseEntity<>(usersList,HttpStatus.OK);
    }

    @GetMapping("/pagging")
    public ResponseEntity<Map<String,Object>>paggingby(@RequestParam(defaultValue = "0")int page,
                                                       @RequestParam(defaultValue = "3")int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Users> usersPage = userService.getPagging(pageable);
        Map<String,Object> data =new HashMap<>();
        data.put("UserName",usersPage.getContent());
        data.put("total",usersPage.getSize());
        data.put("totaLItem",usersPage.getTotalElements());
        data.put("totalPage",usersPage.getTotalPages());
        return new  ResponseEntity<>(data,HttpStatus.OK);


    }

    @GetMapping("/filter")
    public List<Users>filterByAdress(@RequestParam("adress")String adress){
        return userService.filterbyAdress(adress);
    }



}
