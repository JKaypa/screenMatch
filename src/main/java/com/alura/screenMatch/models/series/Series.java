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
    private String id;

    @Column(unique = true, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genres;

    private  String director;
    private  String actors;
    private  String plot;
    private  Integer seasons;
    private  String poster;
    private  Double rating;

    @Transient
    List<Episode> episodes;

    public Series(){}
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
