package com.pinguin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Developers")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "stories", "bugs"})
public class Developer {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "developerId", columnDefinition = "VARCHAR(255)")
    private UUID developerId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "developer", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "developer", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Bug> bugs = new ArrayList<>();
}
