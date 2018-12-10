package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Document
@Data
public class Drama implements Serializable {

    private static final long serialVersionUID = -4168703630301694715L;
    @Id
    @GeneratedValue
    private int id;

    private Integer roleId;

    private Integer step;

    private String url;
}
