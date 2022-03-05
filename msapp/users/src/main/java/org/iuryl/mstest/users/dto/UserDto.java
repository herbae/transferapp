package org.iuryl.mstest.users.dto;

import java.util.ArrayList;
import java.util.List;

import org.iuryl.mstest.users.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;

    private String name;

    private List<AccountDto> accounts = new ArrayList<>();

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
    }
}
