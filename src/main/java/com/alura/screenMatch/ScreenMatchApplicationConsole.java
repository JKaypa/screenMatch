package com.alura.screenMatch;

import com.alura.screenMatch.app.MainApp;
import com.alura.screenMatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplicationConsole implements CommandLineRunner {
	@Autowired
	private SeriesRepository seriesRepository;
	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplicationConsole.class, args);
    }

	@Override
	public void run(String... args) {
		var app = new MainApp(seriesRepository);

		app.start();
	}
}
