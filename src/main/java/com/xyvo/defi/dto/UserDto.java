package com.xyvo.defi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xyvo.defi.domain.profile.Address;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDto extends AuditedDto {

    private long id;

    private String userName;

    private List<Address> addresses;

    private SettingsDto settings;

    public UserDto() {
    }


    @JsonProperty(value = "userId")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(value = "userName", required = true)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setSettings(SettingsDto settingsDto) {
        this.settings = settingsDto;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public SettingsDto getSettings() {
        return settings;
    }
}
