package com.oficial.C1739.Util;


import com.oficial.C1739.Entity.Permiso;
import com.oficial.C1739.Repository.PermisoRepository;
import com.oficial.C1739.Entity.Rol;
import com.oficial.C1739.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaInicial {


    //Con esta clase me encargo de poblar mi bd con datos estaticos que considero necesario para evitar problemas al inicial el proyecto
    @Autowired
    RolRepository rolRepository;

    @Autowired
    PermisoRepository permisoRepository;

    @Bean
    public String cargaDatos(PermisoRepository permisoRepository){
        // creacion de datos inicial, en este caso contamos con 4 roles ya definidos
        Rol rolUsuario= new Rol();
        Rol rolEmprendedor= new Rol();
        Rol rolAdministrador= new Rol();
        Rol rolInversor= new Rol();

        rolUsuario.setNombre("USUARIO");
        rolEmprendedor.setNombre("ROLE_ADMINISTRADOR");
        rolAdministrador.setNombre("ROLE_INVERSIONISTA");
        rolInversor.setNombre("ROLE_INVERSOR");

        // Crea permisos
        Permiso permisoTesteo = new Permiso("PERMISO_TESTEO");

        permisoRepository.save(permisoTesteo);

        Permiso crearProyecto = new Permiso("CREAR_PROYECTO");
        permisoRepository.save(crearProyecto);
        Permiso editarProyecto = new Permiso("EDITAR_PROYECTO");
        permisoRepository.save(editarProyecto);
        Permiso eliminarProyecto = new Permiso("ELIMINAR_PROYECTO");
        permisoRepository.save(eliminarProyecto);
        Permiso publicarProyecto = new Permiso("PUBLICAR_PROYECTO");
        permisoRepository.save(publicarProyecto);
        Permiso visualizarProyecto= new Permiso("VISUALIZAR_PROYECTOS");
        permisoRepository.save(visualizarProyecto);


        rolUsuario.getPermisos().add(permisoTesteo);

        rolEmprendedor.getPermisos().add(crearProyecto);
        rolEmprendedor.getPermisos().add(editarProyecto);
        rolEmprendedor.getPermisos().add(eliminarProyecto);
        rolEmprendedor.getPermisos().add(publicarProyecto);
        rolEmprendedor.getPermisos().add(visualizarProyecto);

        rolRepository.save(rolUsuario);
        rolRepository.save(rolEmprendedor);
        rolRepository.save(rolAdministrador);
        rolRepository.save(rolInversor);

        return "Carga de roles exitosa";

    }


}
