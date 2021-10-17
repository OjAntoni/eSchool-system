package by.tms.schoolmanagementsystem.configuration;

import by.tms.schoolmanagementsystem.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class InterceptorsConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .excludePathPatterns("/user/reg")
                .excludePathPatterns("/user/auth")
                .excludePathPatterns("/user/password/*")
                .excludePathPatterns("/error");
        registry.addInterceptor(new StudentInterceptor())
                .addPathPatterns("student/**")
                .excludePathPatterns("user/announcement/{id}")
                .excludePathPatterns("/lesson/{id}");
        registry.addInterceptor(new TeacherInterceptor())
                .addPathPatterns("/teacher/**")
                .addPathPatterns("/lesson/**")
                .excludePathPatterns("/lesson/{id}")
                .excludePathPatterns("user/announcement/{id}");
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new NotStudentInterceptor())
                .addPathPatterns("/user/announcement/new/**");
        registry.addInterceptor(new AuthorizedInterceptor())
                .addPathPatterns("/user/auth")
                .addPathPatterns("/user/reg");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
}
