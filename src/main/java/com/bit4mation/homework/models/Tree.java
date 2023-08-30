package com.bit4mation.homework.models;

import lombok.*;

import java.util.List;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "trees")
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;
}