package com.oficial.C1739.dto;

import com.oficial.C1739.Enum.State;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ProjectDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private State state;
    private String photoUrl;
    private double targetAmount;
    private Long creatorId;
    private List<Long> teamMemberIds;

}
