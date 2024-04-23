package com.oficial.C1739.Controller;

import com.oficial.C1739.Entity.Project;
import com.oficial.C1739.Service.ProjectService;
import com.oficial.C1739.Service.StorageService;
import com.oficial.C1739.dto.ProjectDTO;
import com.oficial.C1739.dto.SaveProjectDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController @AllArgsConstructor
@RequestMapping("/api/project")
public class ProjectControler {

    private ProjectService projectService;
    private final StorageService storageService;
    private final HttpServletRequest request;
    @PostMapping()
    public ResponseEntity<Project> createProject(@Valid @RequestBody SaveProjectDTO newProjectDto) {

        /*if(!multipartFile.isEmpty()){
            String path = storageService.store(multipartFile);
            String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            String url = ServletUriComponentsBuilder
                    .fromHttpUrl(host)
                    .path("/media/")
                    .path(path)
                    .toUriString();
            newProjectDto.setPhotoUrl(url);
        }*/
        Project savedProject = projectService.saveProject(newProjectDto);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    /*@PostMapping("/media")
    public String uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        String url = "";
        if(!multipartFile.isEmpty()){
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/media/")
                .path(path)
                .toUriString();
        }
        return url;
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el proyecto
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDTO projectDto) {
        Project updatedProject = projectService.updateProject(id, projectDto);
        if (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el proyecto
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Indica que la operación se realizó sin contenido que retornar
    }




}
