package com.oficial.C1739.dto;

import com.oficial.C1739.Enum.State;
import jakarta.validation.constraints.NotBlank;

public class SaveProjectDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private State state;

    private String video_url;
    private String reward;

    public SaveProjectDTO() {
    }

    public SaveProjectDTO(String name, String description, State state, String video_url, String reward) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.video_url = video_url;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
