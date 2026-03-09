package net.engineeringdigest.journalApp.controller;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")   //user-> It is the end point.
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping   //We can get all details.
    public List<User> getAllUsers(){   //getAllUsers kya dega? -> It will provide list of users List<User>
        return userService.getAll();

    }
    @PostMapping   //It creates
    public ResponseEntity<?>createUser(@RequestBody User user){
        userService.saveEntry(user);
        return ResponseEntity.ok("User Created");
    }

    @PutMapping("/{username}")  //It's Update the entity(userName , passWord)
    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveEntry(userInDb);
        return new ResponseEntity<>("User Not Found",HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User userInDb = userService.findByUserName(username);
        if (userInDb != null) {
            userService.deleteById(userInDb.getId());
            return ResponseEntity.ok("User Deleted");
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NO_CONTENT);
    }
}