package org.iuryl.mstest.users.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserEntity {
    
    private @Id @GeneratedValue Long id;
    @NonNull
    private String name;
    @NonNull
    private String username;

}
