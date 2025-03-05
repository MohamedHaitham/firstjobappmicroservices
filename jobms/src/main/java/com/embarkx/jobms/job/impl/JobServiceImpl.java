package com.embarkx.jobms.job.impl;


import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.dto.JobWithCompanyDTO;
import com.embarkx.jobms.job.external.Company;
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

    // since jobRepository is a bean that is managed by springboot, so this constructor can autowire it in the constructor
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job){
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://localhost:8081/companies/"+job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
