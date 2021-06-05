package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VideosDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("results")
    @Builder.Default
    private List<VideoDTO> results = new ArrayList<>();

}
