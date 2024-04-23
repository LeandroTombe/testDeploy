package com.oficial.C1739.dto;

import com.oficial.C1739.Entity.Comment;
import com.oficial.C1739.Entity.Investment;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Enum.State;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProjectDTO {

    private String name;

    private String description;
    private State state;
    private String photoUrl;
    private double targetAmount;

    private int likes;

    private Date launch;
    private Long creator;

    private String teamName;
    private List<Long> teamMemberIds;

    private List<Comment> comments;

    private List<Investment> investments;

}
