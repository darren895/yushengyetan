package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
public class Memory implements Serializable {

    private static final long serialVersionUID = 9054335295935664234L;

    @Id
    private int id;

    private String title;

    private String picUrl;

    @Indexed
    private Boolean state;
}
