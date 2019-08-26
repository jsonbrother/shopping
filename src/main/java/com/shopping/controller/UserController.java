package com.shopping.controller;

import com.alibaba.druid.util.StringUtils;
import com.shopping.controller.vo.UserVO;
import com.shopping.enums.BusinessErrorEnum;
import com.shopping.exception.BusinessException;
import com.shopping.handler.BusinessExceptionHandler;
import com.shopping.result.CommonReturn;
import com.shopping.service.IUserService;
import com.shopping.service.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 用户模块.
 * Created by TongHaiJun
 * 2019/8/20 22:32
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    @ResponseBody
    public CommonReturn login(@RequestParam(name = "telPhone") String telPhone,
                              @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 入参检验
        if (StringUtils.isEmpty(telPhone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 用户登陆服务
        UserDTO userDTO = iUserService.validateLogin(telPhone, this.EncodeByMd5(password));

        // 将登陆凭证加入到用户登陆成功的session内
        this.httpServletRequest.getSession().setAttribute("is_login", true);
        this.httpServletRequest.getSession().setAttribute("login_user", userDTO);

        return CommonReturn.create(null);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ResponseBody
    public CommonReturn register(@RequestParam(name = "telPhone") String telPhone,
                                 @RequestParam(name = "otpCode") String otpCode,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "gender") Integer gender,
                                 @RequestParam(name = "age") Integer age,
                                 @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 验证手机号和对应otpCode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telPhone);
        if (!StringUtils.equals(inSessionOtpCode, otpCode)) {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }

        // 用户的注册流程
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setGender(gender);
        userDTO.setAge(age);
        userDTO.setTelPhone(telPhone);
        userDTO.setRegisterMode("byPhone");
        userDTO.setPassword(this.EncodeByMd5(password));
        iUserService.register(userDTO);

        return CommonReturn.create(null);
    }

    /**
     * 获取otp短信接口
     */
    @PostMapping("getOtp")
    @ResponseBody
    public CommonReturn getOtp(@RequestParam("telPhone") String telPhone) {
        // 需要按照一定的规则生产OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);

        // 讲OTP验证码同对应用户的手机号关联,使用httpSession
        httpServletRequest.getSession().setAttribute(telPhone, optCode);

        // 讲OTP验证码通过短信通道发送给用户
        System.out.println("telPhone = " + telPhone + " & otpCode = " + optCode);

        return CommonReturn.create(null);
    }

    /**
     * 获得用户信息
     */
    @PostMapping("/get")
    @ResponseBody
    public CommonReturn getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        // 调用service服务获取对应id的用户对象返回给前端
        UserDTO userDTO = iUserService.selectById(id);

        // 若获取的对应用户信息不存在
        if (userDTO == null) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }

        // 将核心领域模型用户对象转换为可供UI使用的VO对象
        UserVO userVO = convertFromDTO(userDTO);

        return CommonReturn.create(userVO);
    }

    private String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    private UserVO convertFromDTO(UserDTO userDTO) {
        if (ObjectUtils.isEmpty(userDTO)) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);
        return userVO;
    }
}
