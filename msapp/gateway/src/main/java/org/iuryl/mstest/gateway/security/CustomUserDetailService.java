package org.iuryl.mstest.gateway.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.iuryl.mstest.gateway.client.UsersClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UsersClient usersClient;

	private final Map<String, CustomUser> users = new HashMap<>();

	public CustomUserDetailService(CustomUser... users) {
		for (CustomUser user : users) {
			createUser(user);
		}
	}

	public void createUser(CustomUser user) {
		this.users.put(user.getUsername().toLowerCase(), user);
	}

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load other user details from users service
        return Optional.ofNullable(this.users.get(username.toLowerCase()))
                .flatMap(user ->
					usersClient.getUserByUsername(username.toLowerCase())
						.map(dto -> new CustomUserDetails(dto, user)))
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

}