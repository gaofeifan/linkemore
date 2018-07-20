/**
 * 
 */
package cn.linkmore.enterprise.controller.ent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */

@Api(tags = "stall",description="企业车位", produces = "application/json")
@RestController
@RequestMapping("/stall")
public class EntStallController {

}
