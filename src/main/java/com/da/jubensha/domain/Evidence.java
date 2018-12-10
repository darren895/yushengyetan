package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class Evidence implements Serializable {

    @Id
    private int id;

    @Indexed
    private int placeId;

    private String picUrl;

    private String bgUrl;

    private Boolean state;

    private Integer childId;

    private Integer cost;
}
