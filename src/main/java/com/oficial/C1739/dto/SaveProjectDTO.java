package com.oficial.C1739.dto;

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
public class SaveProjectDTO {


    private String name;
    private String description;
    private String photoUrl;
    private double targetAmount;
    private Date launch;
    private String teamName;
    private List<Long> teamMemberIds;


}
