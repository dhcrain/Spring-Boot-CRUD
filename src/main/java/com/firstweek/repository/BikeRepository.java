package com.firstweek.repository;


import com.firstweek.model.Bike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends PagingAndSortingRepository<Bike, Integer>{

    List<Bike> findByModelYear(Integer modelYear);
    List<Bike> findByModel(String model);
    List<Bike> findByMakeContainingIgnoreCase(String make);
    Page<Bike> findByMakeContainingIgnoreCase(String make, Pageable pageable);

}
