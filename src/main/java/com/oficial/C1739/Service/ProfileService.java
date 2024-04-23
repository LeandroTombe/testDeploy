package com.oficial.C1739.Service;


import com.oficial.C1739.Entity.Profile;
import com.oficial.C1739.dto.ProfileDTO;

public interface ProfileService {

    Profile getProfile(Long id);

    Profile updateProfile(Long id, ProfileDTO profileDTO);

}
