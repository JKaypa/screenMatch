package com.alura.screenMatch.app;

import com.alura.screenMatch.models.series.SeasonDto;
import com.alura.screenMatch.models.series.Series;
import com.alura.screenMatch.models.series.SeriesDto;
import com.alura.screenMatch.repository.SeriesRepository;
import com.alura.screenMatch.service.APIQuery;
import com.alura.screenMatch.service.DataConverter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private final Scanner input = new Scanner(System.in);
    private final DataConverter serializer = new DataConverter();
    private final APIQuery query = new APIQuery();
    private final List<SeriesDto> history = new ArrayList<>();
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6c83494f";
    private final SeriesRepository seriesRepository;

    public MainApp(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }


    public void start () {
        var i = -1;

        while (i != 0) {
            var menu = """
               \n
               ******* Screen Match *******
                Choose an option
            
                [1] - Find a tv series.
                [2] - Find episodes.
                [3] - history.
                [0] - Exit.
            """;
            System.out.println(menu);

            var option = input.nextLine();

            switch (option) {
                case "1" -> printTvSeries();
                case "2" -> printAllEpisodes();
                case "3" -> printHistory();
                case "0" -> i = 0;
                default -> System.out.println("Please enter a valid option!!!!");
            }
        }
    }

    private SeriesDto fetchTvSeries () {
        System.out.println("******* Write the name of the tv series");
        var name = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8);
        var endpoint = BASE_URL + name + API_KEY;
        var json = query.getData(endpoint);
        var tvSeries = serializer.toObject(json, SeriesDto.class);

        Series series = new Series(tvSeries);
        seriesRepository.save(series);
        //history.add(tvSeries);
        return tvSeries;
    }

    private void printTvSeries () {
        System.out.println(fetchTvSeries());
    }

    private void printAllEpisodes () {
        var tvSeries = fetchTvSeries();
        var seasonQuery = "&season=";
        var name = URLEncoder.encode(tvSeries.title(), StandardCharsets.UTF_8);
        var seasonNumber = Integer.parseInt(tvSeries.seasons());

        for (int i = 1; i <= seasonNumber; i++) {
            var endpoint = BASE_URL + name + seasonQuery + i + API_KEY;
            var json = query.getData(endpoint);
            var season = serializer.toObject(json, SeasonDto.class);

            System.out.println("\nSeason " + i);
            season.episodes().forEach(System.out::println);
        }
    }

    private void printHistory() {
        seriesRepository.findAll().forEach(System.out::println);
        //history.forEach(System.out::println);

    }

}
