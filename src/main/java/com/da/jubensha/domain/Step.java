package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class Step implements Serializable {

    private static final long serialVersionUID = -4732322187618180430L;
    @Id
    private int id;

    private int step;
}
