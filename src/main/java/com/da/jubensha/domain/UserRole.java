package com.da.jubensha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8860434344945038221L;

    @Id
    private String sessonId;

    private Integer roleId;

    private Integer firstPoint;

    private Integer secondPoint;
}
