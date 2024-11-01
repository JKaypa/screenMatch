package com.alura.screenMatch.models.series;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Episode {
    private Integer season;
    private String title;
    private Integer episode;
    private Double rate;
    private LocalDate release;

    public Episode(EpisodeDto episode, String season) {
        this.season = Integer.parseInt(season);
        this.title = episode.title();
        this.episode = episode.episode();
        this.rate = !episode.rating().equals("N/A") ? Double.parseDouble(episode.rating()) : null;
        this.release = !episode.released().equals("N/A") ? LocalDate.parse(episode.released()) : null;
    }

    @Override
    public String toString() {
        return
                "title: " + title +
                ", episode: " + episode +
                ", season: " + season +
                ", rate: " + rate +
                ", release: " + release;

    }
}
