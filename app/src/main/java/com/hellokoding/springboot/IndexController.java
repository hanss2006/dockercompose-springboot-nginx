package com.hellokoding.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @GetMapping("/")
    public ResponseEntity<Object> index(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            String remoteAddress="";
            String realAddress="";
            if (request != null) {
                remoteAddress = request.getHeader("X-Forwarded-For");
                if (remoteAddress == null || "".equals(remoteAddress)) {
                    remoteAddress = request.getRemoteAddr();
                }
                realAddress = request.getHeader("X-Real-IP");
            }
            remoteAddress=remoteAddress!=null && remoteAddress.contains(",")
                    ? remoteAddress.split(",")[0]
                    : remoteAddress;
            result.put("ipAddress",remoteAddress);
            result.put("realAddress",realAddress);
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.clear();
            result.put("timestamp", new Date());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("isSuccess",false);
            result.put("message", e.getMessage());
            result.put("data", null);
            return new ResponseEntity<Object>(result,HttpStatus.BAD_REQUEST);
        }
    }

}
