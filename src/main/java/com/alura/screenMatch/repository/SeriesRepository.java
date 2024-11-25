package com.alura.screenMatch.repository;

import com.alura.screenMatch.enums.Genre;
import com.alura.screenMatch.models.episodes.Episode;
import com.alura.screenMatch.models.series.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, String> {
    Series findByTitleIgnoreCase(String title);

    List<Series> getTop5ByOrderByRatingDesc();

    List<Series> getSeriesByGenres(Genre genre);

    //List<Series> findBySeasonsLessThanEqualAndRatingGreaterThanEqual(int season, double rating);
    @Query(value = "SELECT s FROM Series s WHERE seasons <= :season AND rating >= :rating")
    List<Series> getBySeasonAndRating(int season, double rating);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:name%")
    List<Episode> getEpisodeByName(String name);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s.title ILIKE %:seriesName% AND e.rate IS NOT NULL ORDER BY rate DESC LIMIT 5")
    List<Episode> getTop5Episodes(String seriesName);
}
