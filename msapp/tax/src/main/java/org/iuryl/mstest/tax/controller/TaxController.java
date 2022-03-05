package org.iuryl.mstest.tax.controller;

import org.iuryl.mstest.tax.dto.RequestTaxDto;
import org.iuryl.mstest.tax.dto.TaxDto;
import org.iuryl.mstest.tax.repository.TaxRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TaxController {
    
    private TaxRepository repository;

    @PostMapping("/tax")
    public TaxDto applyTax(@RequestBody RequestTaxDto requestTaxDto) {
        return repository.applyTax(requestTaxDto.getAmount());
    }
}
