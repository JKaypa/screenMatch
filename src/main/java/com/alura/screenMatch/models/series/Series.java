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

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(episode -> episode.setSeries(this));
        this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenres() {
        return genres;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public String getPoster() {
        return poster;
    }

    public Double getRating() {
        return rating;
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
               ", rating: " + rating +
               ", episodes: " + episodes;
    }
}
