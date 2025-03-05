package com.embarkx.companyms.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {   // It needs two parameters: object and the type of the id
}
