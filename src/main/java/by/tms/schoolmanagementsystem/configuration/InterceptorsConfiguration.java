package by.tms.schoolmanagementsystem.configuration;

import by.tms.schoolmanagementsystem.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorsConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .excludePathPatterns("/user/reg")
                .excludePathPatterns("/user/auth");
        registry.addInterceptor(new StudentInterceptor())
                .addPathPatterns("student/**");
        registry.addInterceptor(new TeacherInterceptor())
                .addPathPatterns("/teacher/**")
                .addPathPatterns("/lesson/**")
                .excludePathPatterns("/lesson/{id}");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new NotUserInterceptor())
                .addPathPatterns("/user/announcement/**");
    }
}
