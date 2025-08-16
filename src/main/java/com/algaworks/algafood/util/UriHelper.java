package com.algaworks.algafood.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@UtilityClass
public class UriHelper {

    public static void addUriInResponseHeader(Object resourceId) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{cidadeId}")
                .buildAndExpand(resourceId)
                .toUri();

        HttpServletResponse responseHeaders = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();

        responseHeaders.setHeader(HttpHeaders.LOCATION, uri.toString());
    }
}
