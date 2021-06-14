package com.service.filmguide.model;

import com.service.filmguide.controller.movie.response.GenreResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="auto_id")
    private Long auto_id;

    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Override
    public boolean equals(Object genre){
        if(genre instanceof Genre){
            return this.id.equals(((Genre)genre).getId());
        }
        else if(genre instanceof GenreResponse){
            return this.id.equals(((GenreResponse)genre).getId());
        }
        return this.equals(genre);
    }
}
