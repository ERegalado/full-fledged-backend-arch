package com.supernovaic.spring.rest.client;

import com.supernovaic.spring.rest.dto.UserFeignClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@FeignClient(name = "usersFeignClient", url = "${usersFeignClient.address}")
@RequestMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UsersFeignClient {
    @GetMapping("/user/get/{username}")
    @Nullable  UserFeignClientResponse getUser(@NotNull @PathVariable(value = "username") String username);
}
