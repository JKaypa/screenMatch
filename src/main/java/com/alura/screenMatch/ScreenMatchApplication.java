package com.alura.screenMatch;

import com.alura.screenMatch.app.App;
import com.alura.screenMatch.models.series.EpisodeDto;
import com.alura.screenMatch.models.series.SeasonDto;
import com.alura.screenMatch.models.series.SeriesDto;
import com.alura.screenMatch.service.APIQuery;
import com.alura.screenMatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var app = new App();

		app.startApp();
	}
}
