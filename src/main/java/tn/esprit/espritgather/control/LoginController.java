package tn.esprit.espritgather.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import tn.esprit.espritgather.dto.LoginRequest;
import tn.esprit.espritgather.dto.LoginRespone;
import tn.esprit.espritgather.service.LoginServiceImpl;
import tn.esprit.espritgather.utils.JWTUtil;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/login")
public class LoginController {
    private  final AuthenticationManager authenticationManager;
    private final LoginServiceImpl loginService;
    private  final JWTUtil jwtUtil ;

@Autowired
    public LoginController(AuthenticationManager authenticationManager, LoginServiceImpl loginService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
}
@PostMapping
    public ResponseEntity<LoginRespone>login(@RequestBody LoginRequest loginRequest){
      try{
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(loginRequest.getMail(),loginRequest.getPassword())
          );
      }catch (AuthenticationException e){
          return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
        UserDetails userDetails;
      try {
          userDetails = loginService.loadUserByUsername(loginRequest.getMail());
      } catch (UsernameNotFoundException e) {
          return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
     String jwt = jwtUtil.generateToken(userDetails.getUsername());

      return ResponseEntity.ok(new LoginRespone(jwt));
     }

     @PutMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam String mail){
    return new ResponseEntity<>(loginService.forgetPassword(mail),HttpStatus.OK);
     }

}
