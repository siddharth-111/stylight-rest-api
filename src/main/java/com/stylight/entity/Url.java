package com.stylight.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "urls")
@Getter
@Setter
public class Url {

    public Url()
    {

    }
    public Url(String orderedParameter, String prettyUrl) {
        this.orderedParameter = orderedParameter;
        this.prettyUrl = prettyUrl;
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "mappingId", columnDefinition = "VARCHAR(255)")
    @JsonIgnore
    private UUID mappingId;

    private String orderedParameter;

    private String prettyUrl;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonIgnore
    private Date creationDate;

    @Override
    public Object clone() {
        try {
            return (Url) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Url(this.orderedParameter, this.prettyUrl);
        }
    }
}
