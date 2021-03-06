package com.service.filmguide.themoviedb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.service.filmguide.model.Person;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PersonDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("popularity")
    private Float popularity;

    public Person mapToPerson(){
        return Person.builder()
                .person_id(this.id)
                .name(this.name)
                .placeOfBirth(this.placeOfBirth)
                .biography(this.biography)
                .birthday(this.birthday)
                .popularity(this.popularity)
                .profilePath(this.profilePath)
                .build();
    }
}
