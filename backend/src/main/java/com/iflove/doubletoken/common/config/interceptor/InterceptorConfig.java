package com.iflove.doubletoken.common.config.interceptor;


import com.iflove.doubletoken.common.constant.Const;
import com.iflove.doubletoken.common.interceptor.CollectorInterceptor;
import com.iflove.doubletoken.common.interceptor.CorsFilter;
import com.iflove.doubletoken.common.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
    private final CollectorInterceptor collectorInterceptor;
    private final CorsFilter corsFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .order(Const.INTERCEPTOR_ORDER_FIRST)
                .addPathPatterns("/capi/**").excludePathPatterns("/capi/**/public/**");
        registry.addInterceptor(collectorInterceptor)
                .order(Const.INTERCEPTOR_ORDER_SECOND)
                .addPathPatterns("/capi/**");
    }
}
