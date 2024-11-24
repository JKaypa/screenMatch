package com.alura.screenMatch.repository;

import com.alura.screenMatch.models.series.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, String> {}
