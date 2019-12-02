package com.bjfu.jtap.common.controller;

import com.bjfu.jtap.common.service.CommonService;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.utils.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/27  21:54
 */
@Controller
public class CommonController {
    @Autowired
    private CommonService commonService;

    /**
     * 去登陆页面
     * @return
     */
    @GetMapping(value = "/")
    public String toLogin() {
        return "/common/login";
    }

    /**
     * 登陆
     * @return
     */
    @RequestMapping(value = "/login")
    public String userLogin(HttpServletRequest request,User user,String verifyCode) {
        User loginUser = commonService.userLogin(user);
        if(loginUser == null){
            request.setAttribute("msg","用户名或密码错误");
            request.setAttribute("errorUser",user);
            return "/common/login";
        }
        //校验验证码 TODO 演示项目时打开以下注释!!
        /*
        String succCode= (String) request.getSession().getAttribute("imageCode");
        succCode = succCode.toLowerCase();
        String userCode = verifyCode.toLowerCase();
        if(!succCode.equals(userCode)) {
            request.setAttribute("msg","验证码错误");
            request.setAttribute("errorUser",user);
            return "/common/login";
        }*/
        //信息放置session
        request.getSession().setAttribute("user",loginUser);
        //根据权限进入不同页面
        if(loginUser.getRole() == 2) {//学生
            return "/student/index";
        }
        if(loginUser.getRole() == 1) {//教师
            return "/teacher/index";
        }
        return "/common/login";
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public String userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

    /**
     *个人信息管理
     * @param request
     * @return
     */
    @RequestMapping(value = {"/student/personInfo","/teacher/personInfo"})
    public String personInfo(HttpServletRequest request) {
        //从session中获取user
        User user = (User) request.getSession().getAttribute("user");
        //未登陆
        if(user == null) {
            return null;
        }
        request.setAttribute("user",commonService.selectUserById(user.getId()));
        //做权限检验
        if(user.getRole() == 1) {//教师
            return "/teacher/person/personInfo";
        }else if(user.getRole() == 2) {//学生
            return "/student/person/personInfo";
        }else {
            return null;
        }
    }

    /**
     * 个人信息修改,去修改页面
     * @param request
     * @return
     */
    @RequestMapping(value = {"/student/personInfo/toEdit","/teacher/personInfo/toEdit"})
    public String personInfoToEdit(HttpServletRequest request) {
        //从session中获取user
        User user = (User) request.getSession().getAttribute("user");
        //未登陆
        if(user == null) {
            return null;
        }
        request.setAttribute("user",commonService.selectUserById(user.getId()));
        //做权限检验
        if(user.getRole() == 1) {//教师
            return "/teacher/person/personInfoEdit";
        }else if(user.getRole() == 2) {//学生
            return "/student/person/personInfoEdit";
        }else {
            return null;
        }
    }

    /**
     * 用户信息修改
     * @param user
     * @return
     */
    @PostMapping(value = {"/student/personInfo/edit","/teacher/personInfo/edit"})
    public String userInfoEdit(User user, HttpServletRequest request, MultipartFile file) {
        commonService.updateUser(user,file);
        //从session中获取user
        User sessionUser = (User) request.getSession().getAttribute("user");
        //未登陆
        if(sessionUser == null) {
            return null;
        }
        //做权限检验
        if(sessionUser.getRole() == 1) {//教师
            return "redirect:/teacher/personInfo";
        }else if(sessionUser.getRole() == 2) {//学生
            return "redirect:/student/personInfo";
        }else {
            return null;
        }
    }

    @GetMapping("/getcode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

}
