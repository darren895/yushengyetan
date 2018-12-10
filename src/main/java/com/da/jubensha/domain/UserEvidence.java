package com.da.jubensha.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Document
@Data
public class UserEvidence implements Serializable {

    private static final long serialVersionUID = 94555760867417178L;
    @Id
    @GeneratedValue
    private int id;

    private Integer placeId;

    private Integer roleId;

    private Integer evidenceId;

    private String url;

    private Integer childId;

    private Integer step;
}
