package com.software.dev.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 向所有视图模型注入当前 context-path（如 /efmp-job；未配置则为空串），
 * 供模板（templates/index.html、login.html）在 JS 中拼出自适应接口路径。
 * 注意：Thymeleaf 3.1 起 ${#request} 不再默认可用，故通过 Model 注入。
 */
@ControllerAdvice
public class ContextPathAdvice {

    @ModelAttribute("contextPath")
    public String contextPath(HttpServletRequest request) {
        return request.getContextPath();
    }
}
