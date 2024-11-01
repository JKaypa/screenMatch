package com.alura.screenMatch.app;

import com.alura.screenMatch.models.series.EpisodeDto;
import com.alura.screenMatch.models.series.SeasonDto;
import com.alura.screenMatch.models.series.SeriesDto;
import com.alura.screenMatch.service.APIQuery;
import com.alura.screenMatch.service.DataConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
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

        List<SeasonDto> seasons = fetchSeasons(series.seasons(), name);

        out.println(series.title());

        printTop5(seasons);
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

    private void printTop5 (List<SeasonDto> seasons) {
        seasons.stream()
                .flatMap(season -> season.episodes()
                        .stream()
                        .filter(episode -> !episode.rating().equals("N/A"))
                        .sorted(Comparator.comparing(EpisodeDto::rating).reversed()))
                .limit(5)
                .forEach(out::println);
    }


}
