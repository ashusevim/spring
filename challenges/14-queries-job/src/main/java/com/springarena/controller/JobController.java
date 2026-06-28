package com.springarena.controller;

import com.springarena.model.Job;
import com.springarena.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<Job> getAllJobs() {
        // TODO: Get all jobs via jobService
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        // TODO: Get job by id or 404
        return null;
    }

    @GetMapping("/company/{company}")
    public List<Job> getJobsByCompany(@PathVariable String company) {
        // TODO: Filter jobs by company via jobService
        return null;
    }

    @GetMapping("/location/{location}")
    public List<Job> getJobsByLocation(@PathVariable String location) {
        // TODO: Filter jobs by location via jobService
        return null;
    }

    @GetMapping("/remote")
    public List<Job> getRemoteJobs() {
        // TODO: Get remote jobs via jobService
        return null;
    }

    @GetMapping("/salary/{minSalary}")
    public List<Job> getJobsByMinSalary(@PathVariable Double minSalary) {
        // TODO: Filter jobs by minimum salary via jobService
        return null;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        // TODO: Create job and return 201 Created
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        // TODO: Update job and return 200 or 404
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        // TODO: Delete job and return 204 or 404
        return null;
    }
}
