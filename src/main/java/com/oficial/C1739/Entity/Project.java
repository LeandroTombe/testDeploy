package com.oficial.C1739.Entity;

import com.oficial.C1739.Enum.State;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "project")
public class Project extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private long idProject;
    @Column(name = "name_project")
    private String name;
    private String description;
    private State state;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "target_amount")
    private double targetAmount;
    private int likes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario creator;
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



    public Project() {
    }

    public Project(long idProject, String name, String description, State state, String photoUrl, double targetAmount, int likes, List<Usuario> teamMembers, List<Comment> comments, List<Investment> investments) {
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        this.state = state;
        this.photoUrl = photoUrl;
        this.targetAmount = targetAmount;
        this.likes = likes;

        this.teamMembers = teamMembers;
        this.comments = comments;
        this.investments = investments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdProject() {
        return idProject;
    }

    public void setIdProject(long idProject) {
        this.idProject = idProject;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }



    public List<Usuario> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<Usuario> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }
}
