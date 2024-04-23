package com.oficial.C1739.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficial.C1739.Enum.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
@Table(name = "project")
public class Project extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name_project")
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "target_amount")
    private double targetAmount;

    private int likes;

    private Date launch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Usuario creator;

    @Column(name = "team_name")
    private String teamName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_project_id"))
    private List<Usuario> teamMembers;

    //Relacion con comment
    @OneToMany(mappedBy = "project")
    private List<Comment> comments;

    //Relacion con investment
    @OneToMany(mappedBy = "project")
    private List<Investment> investments;


}
