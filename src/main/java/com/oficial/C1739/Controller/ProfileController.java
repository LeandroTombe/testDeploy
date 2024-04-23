package com.oficial.C1739.Controller;


import com.oficial.C1739.Entity.Profile;
import com.oficial.C1739.Entity.Usuario;
import com.oficial.C1739.Repository.UsuarioRepository;
import com.oficial.C1739.Service.AuthenticateService;
import com.oficial.C1739.Service.ProfileService;
import com.oficial.C1739.Service.UserService;
import com.oficial.C1739.dto.ProfileDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    AuthenticateService authenticateService;

    @Autowired
    UserService userService;

    @Autowired
    UsuarioRepository usuarioRepository;

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping()
    public ResponseEntity<ProfileDTO> getProfile(){

        //Identificamos el usuario que esta haciendo la solicitud
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mailUser = authentication.getName();

        //busca el usuario en la DB por el correo
        Usuario usuario = usuarioRepository.findByCorreo(mailUser).orElseThrow(()-> new RuntimeException("Usuario no encontrado."));

        //Recuperamos el perfil del usuario, con el Objeto Usuario podemos acceder al perfil asociado
        Profile profile = usuario.getPerfil();
        if (profile == null){
            return ResponseEntity.notFound().build();
        }

        //Convertimos el profile a un ProfileDTO
        ProfileDTO profileDTO = transformProfileDTO(profile);

        //Retornamos el profileDTO
        return ResponseEntity.ok(profileDTO);

    }
    //Metodo para transformar el profile en un profileDTO
    private ProfileDTO transformProfileDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();

        dto.setNombre(profile.getNombre());
        dto.setApellido(profile.getApellido());
        dto.setCorreo(profile.getCorreo());
        dto.setAge(profile.getAge());
        dto.setAvatar(profile.getAvatar());
        dto.setPhone(profile.getPhone());
        dto.setLinkedin(profile.getLinkedin());
        dto.setGit(profile.getGit());
        dto.setEspecialidad(profile.getEspecialidad());
        dto.setNacionalidad(profile.getNacionalidad());

        return dto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id,
                                                 @Valid @RequestBody ProfileDTO profileDTO){

        //Identificamos el usuario que esta haciendo la solicitud
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String mailUser = authentication.getName();

        //busca el usuario en la DB por el correo
        Usuario usuario = usuarioRepository.findByCorreo(mailUser)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado."));


        //Verificamos si el usuario autenticado tiene permisos para editar el profile
        if(usuario.getPerfil().getIdProfile() != id){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Profile updatedProfile = profileService.updateProfile(id, profileDTO);
        return ResponseEntity.ok(updatedProfile);


    }

}
