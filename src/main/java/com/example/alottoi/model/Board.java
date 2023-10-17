package com.example.alottoi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dtWrite;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dtWrtMdfy;
}
