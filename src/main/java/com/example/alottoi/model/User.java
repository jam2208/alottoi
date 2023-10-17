package com.example.alottoi.model;

import java.io.Serializable;
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
public class User implements Serializable {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String pwd;
	
    private String email;
    
    private String phnNum;
    
    @Temporal(TemporalType.DATE)
    private Date dtSignup;

    @Temporal(TemporalType.DATE)
    private Date dtModify;
}
