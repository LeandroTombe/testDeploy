package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Project;
import com.oficial.C1739.Repository.ProjectRepository;
import com.oficial.C1739.Service.ProjectService;
import com.oficial.C1739.dto.SaveProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project saveProject(SaveProjectDTO newProjectDto) {
        return null;
    }

    @Override
    public Project getProjectById(Long idProject) {
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        return null;
    }

    @Override
    public Project updateProject(Long idProject, SaveProjectDTO newProjectDto) {
        return null;
    }

    @Override
    public Project deleteProject(Long idProject) {
        return null;
    }


 /*   @Override
    public Project saveProject(SaveProjectDTO newProjectDto) {
        /*Project project = new Project();
        project.setName(newProjectDto.getName());
        project.setDescription(newProjectDto.getDescription());
        project.setState(newProjectDto.getState());
        project.setPhotoUrl(newProjectDto.get());
        project.setState(newProjectDto.getState());
        project.setState(newProjectDto.getState());


        return null;//projectRepository.save(project);
    }

    @Override
    public Project getProjectById(Long idProject) {
        Optional<Project> optionalProject = projectRepository.findById(idProject);
        return optionalProject.orElse(null); // Retorna el proyecto si se encuentra, o null si no se encuentra
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll(); // Retorna todos los proyectos
    }

    @Override
    public Project updateProject(Long idProject, SaveProjectDTO newProjectDto) {
        Optional<Project> optionalProject = projectRepository.findById(idProject);
        if (optionalProject.isPresent()) { // Si se encuentra el proyecto
            Project existingProject = optionalProject.get();
            existingProject.setNameProject(newProjectDto.getName());
            existingProject.setDescription(newProjectDto.getDescription());
            existingProject.setReward(newProjectDto.getReward());
            existingProject.setVideo_url(newProjectDto.getVideo_url());
            existingProject.setState(newProjectDto.getState());
            return projectRepository.save(existingProject); // Guarda y retorna el proyecto actualizado
        } else {
            return null; // Si no se encuentra el proyecto, retorna null
        }
    }

    @Override
    public Project deleteProject(Long idProject) {
        projectRepository.deleteById(idProject); // Elimina el proyecto por su ID
        return null;
    }*/



}
