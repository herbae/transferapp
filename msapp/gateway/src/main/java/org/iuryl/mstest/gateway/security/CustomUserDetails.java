package org.iuryl.mstest.gateway.security;

import org.iuryl.mstest.gateway.client.UserForAuthDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails extends CustomUser {

    private long userId;
    private String name;
    
    public CustomUserDetails(UserForAuthDto dto, CustomUser user) {
        super(user.getUsername(), user.getPassword());
        this.userId = dto.getId();
        this.name = dto.getName();
    }
}
