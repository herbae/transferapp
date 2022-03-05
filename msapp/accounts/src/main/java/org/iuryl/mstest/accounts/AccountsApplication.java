package org.iuryl.mstest.accounts;

import javax.servlet.http.HttpServletRequest;

import org.iuryl.mstest.common.InvalidUserIdException;
import org.iuryl.mstest.common.UserInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

	@Bean
    @RequestScope
    public UserInfo getUserId(HttpServletRequest request) {
        return new UserInfo(extractUserId(request));
    }

    private long extractUserId(HttpServletRequest request) {
        String userIdStr = request.getHeader("userId");

        if(userIdStr == null) {
            throw new InvalidUserIdException("NULL");
        }

        try {
            return Long.parseLong(userIdStr);
        } catch(NumberFormatException ex) {
            throw new InvalidUserIdException(userIdStr);
        }
    }

}
