package com.ra.repository;

import com.ra.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size,Long> {
    Size findSizeByName(String name);
}
