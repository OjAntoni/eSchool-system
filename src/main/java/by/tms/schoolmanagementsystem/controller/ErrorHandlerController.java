package by.tms.schoolmanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandlerController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        logger.error(String.format("Error occurred while processing the request to %s with method %s", request.getRequestURL(), request.getMethod()));
        return "error";
    }
}
