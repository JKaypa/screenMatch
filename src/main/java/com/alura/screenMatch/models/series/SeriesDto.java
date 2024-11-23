package com.alura.screenMatch.models.series;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesDto(@JsonAlias("Title") String title,
                        @JsonAlias("Genre") String genre,
                        @JsonAlias("Director") String director,
                        @JsonAlias("Actors") String actors,
                        @JsonAlias("Plot") String plot,
                        @JsonAlias("totalSeasons") String seasons,
                        @JsonAlias("Poster") String poster,
                        @JsonAlias("imdbRating") String rating) {
    @Override
    public String toString() {
        return
               "Title: " + title +
               ", Genre: " + genre +
               ", Director: " + director +
               ", Actors: " + actors +
               ", Plot: " + plot +
               ", Seasons: " + seasons +
               ", Poster1: " + poster +
               ", Rating: " + rating;
    }
}
