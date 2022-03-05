package org.iuryl.mstest.users.dto;

import org.iuryl.mstest.users.model.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForAuthDto {
    
    private long id;

    private String name;

    private String username;

    public UserForAuthDto(UserEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.username = entity.getUsername();
    }
}
