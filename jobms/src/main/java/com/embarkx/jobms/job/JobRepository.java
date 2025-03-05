package com.embarkx.jobms.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {   // It needs two parameters: object and the type of the id
}
