package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Document
@Data
public class Place implements Serializable {

    private static final long serialVersionUID = -7597806832025366943L;
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private Boolean firstPlace;

    private Boolean secondPlace;
}
