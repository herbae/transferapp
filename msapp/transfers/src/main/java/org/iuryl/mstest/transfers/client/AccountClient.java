package org.iuryl.mstest.transfers.client;

import java.util.Optional;

import org.iuryl.mstest.transfers.dto.AccountDto;
import org.iuryl.mstest.transfers.dto.ProcessTransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("accounts")
public interface AccountClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/accounts/number/{number}")
    Optional<AccountDto> getAccountByNumber(@PathVariable("number") String number);

    @RequestMapping(method = RequestMethod.POST, value = "/accounts/transfer")
    String transfer(ProcessTransferDto dto);
}
