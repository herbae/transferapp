package org.iuryl.mstest.transfers.client;

import org.iuryl.mstest.transfers.dto.RequestTaxDto;
import org.iuryl.mstest.transfers.dto.TaxDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("tax")
public interface TaxClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "/tax")
    TaxDto applyTax(RequestTaxDto requestTaxDto);
}
