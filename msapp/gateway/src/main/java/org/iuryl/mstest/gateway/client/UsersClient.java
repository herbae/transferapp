package org.iuryl.mstest.gateway.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("users")
public interface UsersClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/users/username/{username}")
    Optional<UserForAuthDto> getUserByUsername(@PathVariable("username") String username);

}
