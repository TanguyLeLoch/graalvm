package com.natu.graalvm.domain.user.application;

import com.natu.graalvm.domain.user.core.model.AddPairCommand;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.model.UserResponse;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.AlterUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private final RetrieveUser retrieveUser;
    private final AddNewUser addNewUser;
    private final AlterUser alterUser;
    private final UserService userService;


    public UserController(RetrieveUser retrieveUser, AddNewUser addNewUser, AlterUser alterUser, UserService userService) {
        this.retrieveUser = retrieveUser;
        this.addNewUser = addNewUser;
        this.alterUser = alterUser;
        this.userService = userService;
    }

    @PostMapping("/{address}")
    public UserResponse getOrCreate(@PathVariable String address) {
        User user = addNewUser.handleGetOrCreate(address);
        return new UserResponse(user);
    }


    @GetMapping("/{address}")
    public UserResponse getUser(@PathVariable String address) {
        User user = retrieveUser.handle(address);
        return new UserResponse(user);
    }

    @PostMapping("/{userAddress}/pair")
    public UserResponse addPairToUser(@PathVariable String userAddress, @RequestBody AddPairCommand command) {
        return new UserResponse(alterUser.addPair(userAddress, command));
    }

    @DeleteMapping("/{userAddress}/pair/{pairAddress}")
    public UserResponse removePairToUser(@PathVariable String userAddress, @PathVariable String pairAddress) {
        return new UserResponse(alterUser.removePair(userAddress, pairAddress));
    }

    @PostMapping("/{userAddress}/computePnl")
    public Map<String, BigDecimal> computePnl(@PathVariable String userAddress) {
        return userService.computePnl(userAddress);
    }

//    @ExceptionHandler(FunctionalException.class)
//    public String handleFunctional(FunctionalException ex) throws Exception {
//        return ex.getMessage();
//    }
//
//    @ExceptionHandler(Exception.class)
//    public String handleError(Exception ex) throws Exception {
//        log.error("Error: ", ex);
//        throw ex;
//    }
}
