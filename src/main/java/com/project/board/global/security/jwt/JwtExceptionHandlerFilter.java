package com.project.board.global.security.jwt;

import com.project.board.global.exception.CustomAccessNotValidException;
import com.project.board.global.exception.CustomSignatureVerificationException;
import com.project.board.global.response.ErrorCode;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(req, res);
        } catch (CustomAccessNotValidException e) {
            setResponse(res, ErrorCode.JWT_ACCESS_NOT_VALID);
        } catch (CustomSignatureVerificationException e) {
            setResponse(res, ErrorCode.JWT_SIGNATURE_VERIFICATION);
        }
    }

    private void setResponse(HttpServletResponse res, ErrorCode errorCode) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json;charset=UTF-8");

        JSONObject jo = new JSONObject();
        jo.put("result", Result.FAIL);
        jo.put("message", errorCode.getErrorMsg());
        jo.put("errorCode", errorCode);

        res.getWriter().print(jo);
    }

    private enum Result {
        FAIL
    }
}
