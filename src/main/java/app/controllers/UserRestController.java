package app.controllers;

import app.entities.user.User;
import app.services.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "User REST")
@Tag(name = "User REST", description = "API для операций с пользователем(User)")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get list of all User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "users found"),
            @ApiResponse(code = 204, message = "users not found")
    })
    public ResponseEntity<List<User>> getAllUsers(@PageableDefault(sort = {"id"})  Pageable pageable) {
        log.info("getAllUsers: get all users");
        var users = userService.getAllUsers(pageable);
        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(users.getContent(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get User by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user found"),
            @ApiResponse(code = 404, message = "user not found")
    })
    public ResponseEntity<User> getUserById(
            @ApiParam(
                    name = "id",
                    value = "User.id"
            )
            @PathVariable Long id) {
        log.info("getUserById: get user by id. id = {}", id);
        var user = userService.getUserById(id);
        return user.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/auth")
    @ApiOperation(value = "Get authenticated User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user found")
    })
    public ResponseEntity<User> getAuthenticatedUser() {
        log.info("getAuthenticatedUser: get authenticated user");
        return new ResponseEntity<>(userService.getUserByEmail((String) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()),
                HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user added")
    })
    public ResponseEntity<User> addUser(
            @ApiParam(
                    name = "user",
                    value = "User model"
            )
            @RequestBody @Valid User user) {
        log.info("addUser: add new user with email={}", user.getEmail());
        userService.saveUser(user);
        return new ResponseEntity<>(userService.getUserByEmail(user.getEmail()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Edit existed User by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user edited")
    })
    public ResponseEntity<User> updateUser(
            @ApiParam(
                    name = "id",
                    value = "User.id"
            )
            @PathVariable("id") Long id,
            @ApiParam(
                    name = "user",
                    value = "User model"
            )
            @RequestBody User user) {
        log.info("updateUser - update user with id = {}", id);
        userService.updateUser(id, user);
        return new ResponseEntity<>(userService.getUserById(id).orElse(null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user by \"id\"")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user deleted"),
            @ApiResponse(code = 404, message = "user not found")
    })
    public ResponseEntity<Void> deleteUserById(
            @ApiParam(
                    name = "id",
                    value = "User.id"
            )
            @PathVariable Long id) {
        log.info("deleteUserById: delete user with id = {}", id);
        var user = userService.getUserById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
