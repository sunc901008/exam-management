package com.exam.controller.uc;

import com.exam.domain.dto.uc.LoginParam;
import com.exam.domain.dto.uc.ProfileParam;
import com.exam.exception.LocalException;
import com.exam.response.BaseResponse;
import com.exam.service.uc.UcService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户中心
 *
 * @author sunc
 * @date 2020/3/7 17:44
 */

@RestController
@RequestMapping("/uc")
public class UcController {
    private static final Logger logger = Logger.getLogger(UcController.class);

    @Resource
    private UcService ucService;

    /**
     * 用户登录
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse login(@RequestBody String data) throws LocalException {
        LoginParam loginParam = LoginParam.build(data);
        return BaseResponse.success(ucService.login(loginParam));
    }

    /**
     * 用户登出
     */
    @ResponseBody
    @PostMapping("/logout")
    public BaseResponse logout() {
        ucService.logout();
        return BaseResponse.success();
    }

    /**
     * 修改个人信息(修改密码)
     */
    @ResponseBody
    @PostMapping("/profile")
    public BaseResponse profile(@RequestBody String data) throws LocalException {
        ProfileParam param = ProfileParam.build(data);
        return BaseResponse.success(ucService.updatePwd(param.getPassword()));
    }

}
