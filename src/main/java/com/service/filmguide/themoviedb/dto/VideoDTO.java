package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.filmguide.model.Video;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VideoDTO {

    @JsonProperty("site")
    private String site;

    @JsonProperty("key")
    private String key;

    public Video mapToVideo(){
        return Video.builder()
                .site(this.site)
                .key(this.key)
                .build();
    }
}
