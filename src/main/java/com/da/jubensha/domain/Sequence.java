package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class Sequence implements Serializable {

    private static final long serialVersionUID = 1991295666718995483L;
    @Id
    private String id;

    private long value;
}
