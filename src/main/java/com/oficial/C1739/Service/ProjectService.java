package com.oficial.C1739.Service;

import com.oficial.C1739.Entity.Project;
import com.oficial.C1739.dto.SaveProjectDTO;

import java.util.List;

public interface ProjectService {

    Project saveProject(SaveProjectDTO newProjectDto);

    Project getProjectById(Long idProject);

    List<Project> getAllProjects();

    Project updateProject(Long idProject, SaveProjectDTO newProjectDto);

    Project deleteProject(Long idProject);



}
