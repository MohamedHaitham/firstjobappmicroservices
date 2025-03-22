package com.embarkx.jobms.job.impl;


import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import com.embarkx.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
//    private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;    // instead of using a list, we are using a repository to store the jobs

    @Autowired  // this tells springboot to autowire the restTemplate bean. this tells spring boot to provide us the instance of RestTemplate at runtime.
    RestTemplate restTemplate;

    // since jobRepository is a bean that is managed by springboot, so this constructor can autowire it in the constructor
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job){

//        RestTemplate restTemplate = new RestTemplate();   // this is not needed as we have autowired the restTemplate above
        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/"+job.getCompanyId(), Company.class);

        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
        });

        List<Review> reviews = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);
//        jobDTO.setCompany(company);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);

    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedjob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
            if(jobOptional.isPresent()){
                Job job = jobOptional.get();
                job.setTitle(updatedjob.getTitle());
                job.setDescription(updatedjob.getDescription());
                job.setMinSalary(updatedjob.getMinSalary());
                job.setMaxSalary(updatedjob.getMaxSalary());
                job.setLocation(updatedjob.getLocation());
                jobRepository.save(job);
                return true;
            }
        return false;
    }


}
