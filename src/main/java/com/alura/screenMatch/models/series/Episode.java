package com.alura.screenMatch.models.series;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Episode {
    private final Integer season;
    private final String title;
    private final Integer episode;
    private final Double rate;
    private final LocalDate release;

    public Episode(EpisodeDto episode, String season) {
        this.season = Integer.parseInt(season);
        this.title = episode.title();
        this.episode = episode.episode();
        this.rate = !episode.rating().equals("N/A") ? Double.parseDouble(episode.rating()) : null;
        this.release = !episode.released().equals("N/A") ? LocalDate.parse(episode.released()) : null;
    }

    public Integer getSeason() {
        return season;
    }

    public String getTitle() {
        return title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public Double getRate() {
        return rate;
    }

    public LocalDate getRelease() {
        return release;
    }

    private String getReleaseDate () {
        return getRelease().format(DateTimeFormatter.ofPattern("E dd MMMM yyyy"));
    }

    @Override
    public String toString() {
        return
                "title: " + title +
                ", episode: " + episode +
                ", season: " + season +
                ", rate: " + rate +
                ", releaseDate: " + getReleaseDate();

    }
}
