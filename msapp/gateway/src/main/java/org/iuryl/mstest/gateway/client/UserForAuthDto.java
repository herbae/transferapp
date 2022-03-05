package org.iuryl.mstest.gateway.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForAuthDto {
    
    private long id;

    private String name;

    private String username;
}
