package com.embarkx.jobms.job.clients;

import com.embarkx.jobms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {
    @GetMapping("/company/{id}")
    Company getCompanyById(@PathVariable("id") Long id);
}
