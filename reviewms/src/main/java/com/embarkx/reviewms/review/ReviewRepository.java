package com.embarkx.reviewms.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCompanyId(Long companyId);   // this is telling jpa that it has to generate the implementation of this in runtime
}
