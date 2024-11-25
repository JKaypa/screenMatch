package com.alura.screenMatch.app;

import com.alura.screenMatch.enums.Genre;
import com.alura.screenMatch.models.episodes.Episode;
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
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6c83494f";
    private List<Series> seriesList;
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
                [2] - Find episodes of a tv series.
                [3] - Find series by name.
                [4] - Get Top 5 series.
                [5] - Get series by genre.
                [6] - Get series by season and rating.
                [7] - Get episode by name.
                [8] - Get Top 5 episodes.
                [9] - history.
                [0] - Exit.
            """;
            System.out.println(menu);

            var option = input.nextLine();

            switch (option) {
                case "1" -> printTvSeries();
                case "2" -> printAllEpisodes();
                case "3" -> findSeriesByTitle();
                case "4" -> getTop5Series();
                case "5" -> getSeriesByGenre();
                case "6" -> getSeriesWith3SeasonsAndGrateRatings();
                case "7" -> getEpisodeByName();
                case "8" -> getTop5Episodes();
                case "9" -> printHistory();
                case "0" -> i = 0;
                default -> System.out.println("Please enter a valid option!!!!");
            }
        }
    }

    private Series fetchTvSeries () {
        System.out.println("******* Write the name of the tv series");
        var name = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8);
        var endpoint = BASE_URL + name + API_KEY;
        var json = query.getData(endpoint);
        var tvSeries = serializer.toObject(json, SeriesDto.class);

        Series series = new Series(tvSeries);
        seriesRepository.save(series);

        return series;
    }

    private void printTvSeries () {
        System.out.println(fetchTvSeries());
    }

    private void printAllEpisodes () {
        this.printHistory();
        System.out.println("\n******* Write the name of the tv series to get all episodes");
        var seriesName = input.nextLine();

        var optionalSeries = seriesList.stream()
                .filter(series -> series.getTitle()
                        .toLowerCase()
                        .contains(seriesName.toLowerCase())
                )
                .findFirst();

        if (optionalSeries.isEmpty()) {
            System.out.println("Tv series not found, thus episodes neither");
            return;
        }

        var series = optionalSeries.get();
        var seasonQuery = "&season=";
        var name = URLEncoder.encode(series.getTitle(), StandardCharsets.UTF_8);
        var seasonNumber = series.getSeasons();
        List<List<Episode>> episodeList = new ArrayList<>();

        for (int i = 1; i <= seasonNumber; i++) {
            int currentSeason = i;
            String endpoint = BASE_URL + name + seasonQuery + currentSeason + API_KEY;
            String json = query.getData(endpoint);
            SeasonDto season = serializer.toObject(json, SeasonDto.class);

            var episodes = season.episodes()
                    .stream()
                    .map(episodeDto -> new Episode(episodeDto, currentSeason))
                    .toList();

            episodeList.add(episodes);
            System.out.println("\nSeason " + season.season());
            season.episodes().forEach(System.out::println);
        }

        var allEpisodes = episodeList
                .stream()
                .flatMap(episodes -> episodes
                        .stream()
                        .map(episode -> episode))
                .toList();

        series.setEpisodes(allEpisodes);

        seriesRepository.save(series);
    }

    private void findSeriesByTitle() {
        System.out.println("******* Write the name of the tv series");
        var title = input.nextLine();
        var series = seriesRepository.findByTitleIgnoreCase(title);

        if(series == null){
            System.out.println("Series not found.");
            return;
        }

        System.out.println(series);
    }

    private void getTop5Series(){
        seriesRepository.getTop5ByOrderByRatingDesc().forEach(System.out::println);
    }

    private void getSeriesByGenre(){
        System.out.println("Write down the genre");
        var genreInput = input.nextLine();
        var genre = Genre.fromString(genreInput);
        seriesRepository.getSeriesByGenres(genre).forEach(System.out::println);
    }

    private void getSeriesWith3SeasonsAndGrateRatings() {
        System.out.println("Introduce max season:");
        var seasonString = input.nextLine();
        System.out.println("Introduce min rating:");
        var ratingString = input.nextLine();

        var season = Integer.parseInt(seasonString);
        var rating = Integer.parseInt(ratingString);

        var series = seriesRepository.getBySeasonAndRating(season, rating);

        if(series.isEmpty()){
            System.out.println("No such series");
        }

        series.forEach(System.out::println);
    }

    private void getEpisodeByName(){
        System.out.println("Insert episode name: ");
        var name = input.nextLine();

        seriesRepository.getEpisodeByName(name).forEach(episode ->
                System.out.println("series: " + episode.getSeries().getTitle() +
                        ", episode: " + episode.getTitle() + ", season: " + episode.getSeason()));
    }

    private void getTop5Episodes(){
        System.out.println("Write series name to get top 5 episodes: ");
        var seriesName = input.nextLine();

        seriesRepository.getTop5Episodes(seriesName).forEach(System.out::println);
    }

    private void printHistory() {
        seriesRepository.findAll().forEach(System.out::println);


    }

}
