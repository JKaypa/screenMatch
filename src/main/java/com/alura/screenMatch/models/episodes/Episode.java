package com.alura.screenMatch.models.episodes;

import com.alura.screenMatch.models.series.Series;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;
    private Integer season;
    private Integer episode;
    private Double rate;
    private LocalDate release;

    @ManyToOne
    private Series series;

    public Episode(){}
    public Episode(EpisodeDto episode, int season) {
        this.season = season;
        this.title = episode.title();
        this.episode = episode.episode();
        this.rate = !episode.rating().equals("N/A") ? Double.parseDouble(episode.rating()) : null;
        this.release = !episode.released().equals("N/A") ? LocalDate.parse(episode.released()) : null;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
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
        if(getRelease() != null) {
            return getRelease()
                    .format(DateTimeFormatter.ofPattern("E dd MMMM yyyy"));
        }

        return "No date registered";
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
