package com.alura.screenMatch.models.series;

import com.alura.screenMatch.enums.Genre;
import com.alura.screenMatch.models.episodes.Episode;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String title;

    @Enumerated(EnumType.STRING)
    Genre genres;
    String director;
    String actors;
    String plot;
    Integer seasons;
    String poster;
    Double rating;

    @Transient
    List<Episode> episodes;

    public Series(SeriesDto series) {
        this.title = series.title();
        this.genres = Genre.fromString(series.genre().split(",")[0].trim());
        this.director = series.director();
        this.actors = series.actors();
        this.plot = series.plot();
        this.seasons = Integer.parseInt(series.seasons());
        this.poster = series.poster();
        this.rating = OptionalDouble.of(Double.parseDouble(series.rating())).orElse(0);
    }

    @Override
    public String toString() {
        return
               "title: " + title +
               ", genre: " + genres +
               ", director: " + director +
               ", actors: " + actors +
               ", plot: " + plot +
               ", seasons: " + seasons +
               ", poster: " + poster +
               ", rating: " + rating;
    }
}
