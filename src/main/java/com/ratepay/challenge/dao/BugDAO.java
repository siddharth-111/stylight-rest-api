package com.ratepay.challenge.dao;

import com.ratepay.challenge.model.enums.Priority;
import com.ratepay.challenge.model.enums.Status;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Bugs")
public class BugDAO {

    @Id
    private UUID issueId;

    private String title;

    private String description;

    private UUID developerId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;
}
