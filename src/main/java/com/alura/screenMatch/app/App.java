package com.alura.screenMatch.app;

import com.alura.screenMatch.models.series.Episode;
import com.alura.screenMatch.models.series.SeasonDto;
import com.alura.screenMatch.models.series.SeriesDto;
import com.alura.screenMatch.service.APIQuery;
import com.alura.screenMatch.service.DataConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

public class App {
    private final Scanner input = new Scanner(in);
    private final DataConverter converter = new DataConverter();
    private static final APIQuery query = new APIQuery();
    private static final String BASE_URL = "https://www.omdbapi.com/?t=";
    private static final String SEASON = "&season=";
    private static final String API_KEY = "&apikey=6c83494f";

    public void startApp () {
        out.println("****** Screen Match *******");
        out.println("Write the series name you'd like to see:");

        String name = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8);
        SeriesDto series = this.fetchSeries(name);

        out.println(series.title());

        List<SeasonDto> seasons = fetchSeasons(series.seasons(), name);

        out.println("Write the year you'd like to see the episodes from:");

        var year = Integer.parseInt(input.nextLine());
        var episodes = getAllEpisodes(seasons);

        filterEpisodesByYear(episodes, year);


    }

    private SeriesDto fetchSeries (String name) {
        String endpoint = BASE_URL + name + API_KEY;
        String json = query.getData(endpoint);
        return converter.toObject(json, SeriesDto.class);
    }

    private List<SeasonDto> fetchSeasons (String seasons, String name) {
        int totalSeasons = Integer.parseInt((seasons));

        List<SeasonDto> seasonsSeries = new ArrayList<>();

        for (int i = 1; i <= totalSeasons; i++) {
            String endpoint = BASE_URL + name + SEASON + i + API_KEY;
            String json = query.getData(endpoint);
            var season = converter.toObject(json, SeasonDto.class);
            seasonsSeries.add(season);
        }

        return seasonsSeries;
    }

    private List<Episode> getAllEpisodes(List<SeasonDto> seasons) {
        return seasons.stream()
                .flatMap(season -> season.episodes()
                        .stream()
                        .map(episode -> new Episode(episode, season.season())))
                .toList();
    }

    private void filterEpisodesByYear (List<Episode> episodes, int year) {
        var localYear = LocalDate.of(year, 1, 1);
        episodes.stream()
                .filter(episode ->
                        episode.getRelease() != null && episode.getRelease()
                                .isAfter(localYear))
                .forEach(out::println);
    }

}
