package com.alura.screenMatch.models.series;

import com.alura.screenMatch.models.episodes.EpisodeDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonDto(@JsonAlias("Season") String season,
                               @JsonAlias("Episodes")List<EpisodeDto> episodes) {}
