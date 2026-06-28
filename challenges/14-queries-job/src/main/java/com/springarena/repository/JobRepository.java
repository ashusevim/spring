package com.springarena.repository;

import com.springarena.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompany(String company);
    List<Job> findByLocation(String location);
    List<Job> findByRemote(boolean remote);
    List<Job> findBySalaryGreaterThanEqual(Double salary);
}
