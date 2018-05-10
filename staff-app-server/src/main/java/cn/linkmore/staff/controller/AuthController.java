package cn.linkmore.staff.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="用户管理")
@RestController
public class AuthController {
	@ApiOperation("创建用户")
    @PostMapping("/users")
    public void create( ) { 
    }

    @ApiOperation("用户详情")
    @GetMapping("/users/{id}")
    public void findById(@PathVariable Long id) { 
    }
}
