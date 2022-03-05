package org.iuryl.mstest.transfers;

import javax.servlet.http.HttpServletRequest;

import org.iuryl.mstest.common.InvalidUserIdException;
import org.iuryl.mstest.common.UserInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
@EnableFeignClients
public class TransfersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransfersApplication.class, args);
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
