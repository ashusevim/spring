package com.springarena.service;

import com.springarena.model.Job;
import com.springarena.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> getAllJobs() {
        // TODO: Get all jobs
        return null;
    }

    @Override
    public Optional<Job> getJobById(Long id) {
        // TODO: Get job by id
        return Optional.empty();
    }

    @Override
    public List<Job> getJobsByCompany(String company) {
        // TODO: Filter by company using custom query
        return null;
    }

    @Override
    public List<Job> getJobsByLocation(String location) {
        // TODO: Filter by location using custom query
        return null;
    }

    @Override
    public List<Job> getRemoteJobs() {
        // TODO: Filter by remote using custom query
        return null;
    }

    @Override
    public List<Job> getJobsByMinSalary(Double minSalary) {
        // TODO: Filter by min salary using custom query
        return null;
    }

    @Override
    public Job createJob(Job job) {
        // TODO: Create a job
        return null;
    }

    @Override
    public Optional<Job> updateJob(Long id, Job job) {
        // TODO: Update a job if exists
        return Optional.empty();
    }

    @Override
    public boolean deleteJob(Long id) {
        // TODO: Delete job by id and return true if existed, false otherwise
        return false;
    }
}
