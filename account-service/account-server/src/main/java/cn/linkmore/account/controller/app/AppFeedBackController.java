package cn.linkmore.account.controller.app;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.service.FeedbackService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 投诉建议
 * @author   GFF
 * @Date     2018年5月16日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/app/feedback")
@Validated
@Api(tags="Feedback",description="投诉建议")
public class AppFeedBackController {
	@Resource
	private FeedbackService feedbackService;

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="投诉建议保存",notes="内容不能为空", consumes = "application/json")
	public ResponseEntity<?> save(
							      @NotBlank(message = "内容不能为空")
								  @RequestParam(value="content",required=true)
								  @ApiParam(value="内容",required=true)String content,HttpServletRequest request) {
		try {
			this.feedbackService.save(content,request);
			return ResponseEntity.success("保存成功", request);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION.code, e.getMessage(), request);
		}
	}
}
