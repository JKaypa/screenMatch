package com.alura.screenMatch.models.series;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesDto(@JsonAlias("Title") String title,
                        @JsonAlias("totalSeasons") String seasons,
                        @JsonAlias("imdbRating") String rating) {}
