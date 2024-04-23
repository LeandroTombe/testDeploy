package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Project;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Enum.State;
import com.oficial.C1739.Repository.ProjectRepository;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.oficial.C1739.Service.ProjectService;
import com.oficial.C1739.dto.ProjectDTO;
import com.oficial.C1739.dto.SaveProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Project saveProject(SaveProjectDTO newProjectDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mailUser = authentication.getName();
        Usuario usuario = usuarioRepository.findByCorreo(mailUser).orElseThrow(()-> new RuntimeException("Usuario no encontrado."));
        List<Usuario> myTeam = new ArrayList<>();

        newProjectDto.getTeamMemberIds().forEach(
                (id) ->{
                    Optional<Usuario> optUser = usuarioRepository.findById(id);
                    myTeam.add(optUser.get());

                }
        );
        Project project = Project.builder()
                .name(newProjectDto.getName())
                .description(newProjectDto.getDescription())
                .targetAmount(newProjectDto.getTargetAmount())
                .launch(newProjectDto.getLaunch())
                .teamName(newProjectDto.getTeamName())
                .photoUrl(newProjectDto.getPhotoUrl())
                .state(State.PENDING_REVIEW)
                .creator(usuario)
                .likes(0)
                .teamMembers(myTeam)
                .build();
        projectRepository.save(project);

        return project;
    }

    @Override
    public Project getProjectById(Long idProject) {
        Optional<Project> project =  projectRepository.findById(idProject);
        if (project.isPresent()) {
            return project.get();
        }
        else{
            throw new RuntimeException("El proyecto no existe");
        }
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project updateProject(Long idProject, ProjectDTO newProjectDto) {
        List<Usuario> myTeam = new ArrayList<>();

        newProjectDto.getTeamMemberIds().forEach(
                (id) ->{
                    Optional<Usuario> optUser = usuarioRepository.findById(id);
                    myTeam.add(optUser.get());

                }
        );
        System.out.println(myTeam.toString());
        Project project = this.getProjectById(idProject);
        project.setName(newProjectDto.getName());
        project.setDescription(newProjectDto.getDescription());
        project.setTargetAmount(newProjectDto.getTargetAmount());
        project.setLaunch(newProjectDto.getLaunch());
        project.setTeamName(newProjectDto.getTeamName());
        project.setPhotoUrl(newProjectDto.getPhotoUrl());
        project.setTeamMembers(myTeam);
        projectRepository.save(project);

        return project;
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
