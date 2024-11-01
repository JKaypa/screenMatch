package com.alura.screenMatch.models.series;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeDto(@JsonAlias("Title") String title,
                         @JsonAlias("Released") String released,
                         @JsonAlias("Episode") int episode,
                         @JsonAlias("imdbRating") String rating) {}
