package org.iuryl.mstest.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.iuryl.mstest.gateway.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthFilter extends ZuulFilter {
    
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        
        RequestContext ctx = RequestContext.getCurrentContext();
        
        CustomUserDetails userDetails =
            (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        log.debug("Adding user information to request: {}", userDetails.getUserId());
        ctx.addZuulRequestHeader("userId", String.valueOf(userDetails.getUserId()));
        return null;
    }
}
