package com.itheima.interceptors;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.itheima.entity.Employee;
import com.itheima.mapper.EmployeeMapper;
import com.itheima.result.Result;
import com.itheima.utils.JwtUtils;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component //把LoginInterceptor注入到ioc容器
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行浏览器预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 1. 从请求头获取 token
        String token = request.getHeader("Authorization");

        // 2. 没带 token
        if (token == null || token.isEmpty()){
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.error("未登录")));
            return false;
        }

        try {
            // 3. 解析 token
            Map<String, Object> map = JwtUtils.parseToken(token);

            // 4. 从 token 中取出用户名和更新时间
            String username = (String) map.get("username");
            String updateTime = (String) map.get("updateTime");

            // 5. 去数据库查当前用户
            Employee e = employeeMapper.findByUsername(username);



            //6. 用户不存在.因为数据库里的用户不是永远都还在，可能这个员工后来被删了，所以要判断不为null
            if(e == null){
                throw new RuntimeException("用户不存在");
            }

            // 7. 数据库中的更新时间和 token 中的不一致，说明 token 已失效
            String dbUpdateTime = e.getUpdateTime() == null ? "" : e.getUpdateTime().toString();
            if(!updateTime.equals(dbUpdateTime)){
                throw new RuntimeException("token已失效");
            }

            // 8. 校验通过，把当前登录用户信息存入 ThreadLocal
            ThreadLocalUtil.set(map);
            return true;
        } catch (TokenExpiredException e) {  //解决用户不存在和过期不能并存的问题
            //设置http状态响应码401
            response.setStatus(401);
            //解决api报错：返回的数据格式应当是 JSON
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(Result.error("token已过期，请重新登录")));
            //如果用 fastjson2
            //response.getWriter().write(JSON.toJSONString(Result.error("未登录")));
            return false;
        }catch (JWTVerificationException e){
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(Result.error("token无效")));
            return false;
        }catch (RuntimeException e) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(Result.error(e.getMessage())));
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
