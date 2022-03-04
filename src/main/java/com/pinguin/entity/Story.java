package com.pinguin.entity;

import com.pinguin.model.enums.StoryStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Stories")
@Getter
@Setter
public class Story {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "issueId", columnDefinition = "VARCHAR(255)")
    private UUID issueId;

    private String title;

    private String description;

    private int points;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "developer", referencedColumnName = "developerId")
    private Developer developer;

    @Enumerated(EnumType.STRING)
    private StoryStatus storyStatus;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;
}
