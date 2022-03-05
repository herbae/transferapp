package org.iuryl.mstest.users.client;

import java.util.List;

import org.iuryl.mstest.users.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("accounts")
public interface AccountClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/public/accounts/users/{userId}")
    List<AccountDto> getAccountsByUserId(@PathVariable("userId") Long userId);
}
