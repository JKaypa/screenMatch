package com.alura.screenMatch.models.episodes;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeDto(@JsonAlias("Title") String title,
                         @JsonAlias("Released") String released,
                         @JsonAlias("Episode") int episode,
                         @JsonAlias("imdbRating") String rating) {
    @Override
    public String toString() {
        return
               "Title: " + title +
               ", Released: " + released +
               ", Episode: " + episode +
               ", Rating: " + rating;
    }
}
