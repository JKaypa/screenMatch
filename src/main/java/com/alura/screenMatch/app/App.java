package com.alura.screenMatch.app;

import com.alura.screenMatch.models.series.Episode;
import com.alura.screenMatch.models.series.SeasonDto;
import com.alura.screenMatch.models.series.SeriesDto;
import com.alura.screenMatch.service.APIQuery;
import com.alura.screenMatch.service.DataConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class App {
    private final Scanner input = new Scanner(in);
    private final DataConverter converter = new DataConverter();
    private static final APIQuery query = new APIQuery();
    private static final String BASE_URL = "https://www.omdbapi.com/?t=";
    private static final String SEASON = "&season=";
    private static final String API_KEY = "&apikey=6c83494f";

    public void startApp () {
        out.println("******* Screen Match *******");
        out.println("\n******* Write the series name you'd like to see:");

        String name = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8);
        SeriesDto series = this.fetchSeries(name);

        if (series.title() == null) {
            out.println("Series not found!");
            return;
        };

        out.println("Name: " + series.title());

        List<SeasonDto> seasons = fetchSeasons(series.seasons(), name);

        out.println("\n******* Write the year you'd like to see the episodes from:");
        var year = Integer.parseInt(input.nextLine());
        var allEpisodes = parseAllEpisodes(seasons);

       filterEpisodesByYear(allEpisodes, year);

       printTop5Episodes(allEpisodes);

        out.println("\n******* Write the title part of the episode you want to find:");
        var titlePart = input.nextLine();

        episodeByName(allEpisodes, titlePart);
        rateSeasonByAverage(allEpisodes);

        statistics(allEpisodes);


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

    private List<Episode> parseAllEpisodes(List<SeasonDto> seasons) {
        return seasons.stream()
                .flatMap(season -> season.episodes()
                        .stream()
                        .map(episode -> new Episode(episode, season.season())))
                .toList();
    }

    private void printTop5Episodes (List<Episode> episodes) {
        out.println("\n******* Top 5 episodes:");
        episodes.stream()
                .filter(episode -> episode.getRate() != null)
                .sorted(Comparator.comparing(Episode::getRate).reversed())
                .limit(5)
                .forEach(out::println);
    }

    private void filterEpisodesByYear (List<Episode> episodes, int year) {
        var localYear = LocalDate.of(year, 1, 1);
        episodes.stream()
                .filter(episode ->
                        episode.getRelease() != null && episode.getRelease()
                                .isAfter(localYear))
                .forEach(out::println);
    }

    private void episodeByName (List<Episode> episodes, String titlePart) {
        var episodeFound = episodes.stream()
                .filter(episode ->
                        episode.getTitle()
                                .toLowerCase()
                                .contains(titlePart.toLowerCase()))
                .toList();

        if (episodeFound.isEmpty())
            out.println("Episodes not found!");
        else episodeFound.forEach(out::println);

        //another way
//        firstEpisode.ifPresent(out::println);
//        if (firstEpisode.isEmpty()) out.println("Episode not found!");
    }

    public void rateSeasonByAverage (List<Episode> episodes) {
        Map<String, Double> seasonRate = episodes.stream()
                .filter(episode -> episode.getRate() != null)
                .collect(Collectors.groupingBy(episode -> "Season: " + episode.getSeason(),
                        Collectors.averagingDouble(Episode::getRate)));

        out.println(seasonRate);
    }

    public void statistics (List<Episode> episodes) {
        DoubleSummaryStatistics statistics = episodes.stream()
                .filter(episode -> episode.getRate() != null)
                .collect(Collectors.summarizingDouble(Episode::getRate));

        out.println("Highest score: " + statistics.getMax());
        out.println("Lowest score: " + statistics.getMin());
        out.println("Average score: " + statistics.getAverage());
        out.println("Total episodes counted: " + statistics.getCount());
    }

}
