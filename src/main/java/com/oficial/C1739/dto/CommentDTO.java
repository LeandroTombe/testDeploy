package com.oficial.C1739.dto;

import com.oficial.C1739.Entity.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class CommentDTO {


    @NotNull(message = "El id del comentario no puede ser nulo")
    private Long idComment;

    @NotNull(message = "La fecha del comentario no puede ser nula")
    private Date dateComment;

    @NotBlank(message = "El texto del comentario no puede estar vacío")
    @Size(max = 500, message = "El texto del comentario no puede tener más de 500 caracteres")
    private String textComment;

    @NotNull(message = "El proyecto no puede ser nulo")
    private Project project;


    public CommentDTO() {
    }

    public long getIdComment() {
        return idComment;
    }

    public void setIdComment(long idComment) {
        this.idComment = idComment;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
