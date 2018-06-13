package cn.linkmore.account.controller.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * Controller - 账号
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Account",description="用户账号")
@RestController
@RequestMapping("app/accounts")
public class AppAccountController {
}
