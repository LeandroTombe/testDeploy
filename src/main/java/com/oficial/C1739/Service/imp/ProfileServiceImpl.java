package com.oficial.C1739.Service.imp;

import com.oficial.C1739.Entity.Profile;
import com.oficial.C1739.Repository.ProfileRepository;
import com.oficial.C1739.Service.ProfileService;
import com.oficial.C1739.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;


    @Override
    public Profile getProfile(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public Profile updateProfile(Long id, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(id).orElse(new Profile());

        //Mapeo de ProfileDTO a la entidad Profile
        updateProfileEntity(profile, profileDTO);

        return profileRepository.save(profile);
    }

    public void updateProfileEntity(Profile profile,ProfileDTO profileDTO){

        profile.setAge(profileDTO.getAge());
        profile.setAvatar(profileDTO.getAvatar());
        profile.setPhone(profileDTO.getPhone());
        profile.setLinkedin(profileDTO.getLinkedin());
        profile.setGit(profileDTO.getGit());
        profile.setEspecialidad(profileDTO.getEspecialidad());
        profile.setNacionalidad(profileDTO.getNacionalidad());

    }



}
