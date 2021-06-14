package com.service.filmguide.model;

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
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "person_id")
    private Integer person_id;

    @Column(name = "name")
    private String name;

    @Column(name="place_of_birth")
    private String placeOfBirth;

    @Column(name="biography")
    private String biography;

    @Column(name="birthday")
    private String birthday;

    @Column(name="profile_path")
    private String profilePath;

    @Column(name="popularity")
    private Float popularity;

    @Override
    public boolean equals(Object person2){
        if(person2 instanceof Person){
            return this.person_id.equals(((Person)person2).getPerson_id());
        }
        return this.equals(person2);
    }
}
