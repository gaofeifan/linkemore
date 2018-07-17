package cn.linkmore.enterprise.controller.staff;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * Controller -B端接口
 * @author liwenlong
 * @version 2.0
 *
 */
@RestController
@Api(tags="Demo",description="例子")
@RequestMapping(value="/staff/demo")
public class StaffDemoController {

}
