package com.bit4mation.homework.models;

import lombok.*;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "nodes")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "value")
    private int value;

    @Column(name = "is_leaf", columnDefinition = "boolean default true")
    private boolean isLeaf = true;
}