package com.springarena.service;

import com.springarena.model.Job;
import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> getAllJobs();
    Optional<Job> getJobById(Long id);
    List<Job> getJobsByCompany(String company);
    List<Job> getJobsByLocation(String location);
    List<Job> getRemoteJobs();
    List<Job> getJobsByMinSalary(Double minSalary);
    Job createJob(Job job);
    Optional<Job> updateJob(Long id, Job job);
    boolean deleteJob(Long id);
}
