package org.iuryl.mstest.exchange.controller;

import org.iuryl.mstest.exchange.dto.ExchangeDto;
import org.iuryl.mstest.exchange.dto.ExchangeOutDto;
import org.iuryl.mstest.exchange.service.ExchangeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ExchangeController {
    
    private ExchangeService service;

    @PostMapping("/exchange")
    public ExchangeOutDto convert(@RequestBody ExchangeDto dto) {
        
        return service.convert(dto);
    }
}
