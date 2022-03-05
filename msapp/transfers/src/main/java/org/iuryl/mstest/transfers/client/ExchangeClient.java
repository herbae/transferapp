package org.iuryl.mstest.transfers.client;

import org.iuryl.mstest.transfers.dto.ExchangeDto;
import org.iuryl.mstest.transfers.dto.ExchangeOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("exchange")
public interface ExchangeClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "/exchange")
    ExchangeOutDto convert(ExchangeDto dto);
}
