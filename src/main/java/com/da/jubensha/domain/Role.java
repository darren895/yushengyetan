package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
@Document
public class Role implements Serializable {

    private static final long serialVersionUID = -7359419276420675243L;
    @Id
    @GeneratedValue
    private int id;

    private String name;
}
