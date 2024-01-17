package com.ra.repository;

import com.ra.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color,Long> {
    Color findColorByName(String colorName);
    Boolean existsColorByName(String colorName);
}
