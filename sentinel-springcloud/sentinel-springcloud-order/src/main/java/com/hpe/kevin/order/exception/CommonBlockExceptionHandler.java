package com.hpe.kevin.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.kevin.order.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BlockExcpetion 异常统一处理
 * 如果接口要定制不同的异常处理, 则可以使用 @SentinelResource 注解
 */
@Component
@Slf4j
public class CommonBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       BlockException e) throws Exception {
        log.info("BlockExceptionHandler BlockException======" + e.getRule());
        R r = null;

        if (e instanceof FlowException) {
            r= R.error(100, "接口限流了");
        } else if (e instanceof DegradeException) {
            r= R.error(101, "服务降级了");
        } else if (e instanceof ParamFlowException) {
            r= R.error(102, "热点参数限流了");
        } else if (e instanceof SystemBlockException) {
            r= R.error(103, "触发系统保护规则了");
        } else if (e instanceof AuthorityException) {
            r= R.error(104, "授权规则不通过");
        }

        // 返回 json 数据
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), r);
    }
}
