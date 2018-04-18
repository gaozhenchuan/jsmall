package org.jsmall.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsmall.service.common.login.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginController {

//    @Resource 
//    private Producer kaptchaProducer; 
//
//    @Autowired 
//    private IRentBikeService rentBikeService;

	@Autowired(required = true)
    private IUserService userService;

    /** 
     * 跳转到登录页面 
     * 
     * @return login.jsp 
     */ 
    @RequestMapping(value = "/loginTurn")
    public String loginTurn() { 
    	if (userService.getUserCount(1003)) {
    		return "login1"; 
    	}
        return "login2"; 
    }

    /** 
      * 获取验证码图片 
      * 
      * @param request 
      * @param response 
      * @return 
      * @throws java.io.IOException Created 2017年1月17日 下午5:07:28 
      * @author ccg 
      */ 
    @RequestMapping("/getkaptchaCode") 
    public ModelAndView getKaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
//        //生成验证码文本 
//        String capText = kaptchaProducer.createText(); 
//        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText); 
//        System.out.println("生成验证码文本====" + capText); 
//        //利用生成的字符串构建图片 
//        BufferedImage bi = kaptchaProducer.createImage(capText); 
//        ServletOutputStream out = response.getOutputStream(); 
//        ImageIO.write(bi, "jpg", out); 
//
//        try {
//            out.flush();
//        } finally { 
//            out.close();
//        } 
        return null;
    }

    /** 
     * login:ajax异步校验登录请求. <br/> 
     * 
     * @param request 
     * @return 校验结果 
     * @author 
     */
   @RequestMapping(value = "/longin")
   public String index() {
       return "index2"; 
   }

    /** 
      * login:ajax异步校验登录请求. <br/> 
      * 
      * @param request 
      * @return 校验结果 
      * @author 
      */
    @RequestMapping("/login")
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {

        //String fileName = file.getOriginalFilename();
//        String path = request.getSession().getServletContext().getRealPath("login"); 
        JSONObject json = new JSONObject();
//        boolean kaptchaFlag = this.checkKaptcha(request, kaptcha);
//        json.put("kaptchaFlag", kaptchaFlag);
//
//        if (kaptchaFlag) { 
//            UserBase userBase = new UserBase();
//            userBase.setUserPhone(userPhone);
//            UserBase user = rentBikeService.selectUserByUserPhone(userBase);
//            boolean userFlag = false;
//            if (user != null) {
//                userFlag = true;
//                request.getSession().setAttribute("user", user);
//                SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUserPhone(), user.getPassword()));
//            } 
//            json.put("userFlag", userFlag); 
//        }
        return json; 
    }
}
