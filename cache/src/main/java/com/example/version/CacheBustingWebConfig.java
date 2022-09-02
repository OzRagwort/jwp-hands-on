package com.example.version;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CacheBustingWebConfig implements WebMvcConfigurer {

    public static final String PREFIX_STATIC_RESOURCES = "/resources";
    public static final Duration ONE_YEAR = Duration.ofDays(365);
    private final ResourceVersion version;

    @Autowired

    public CacheBustingWebConfig(ResourceVersion version) {
        this.version = version;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl.maxAge(ONE_YEAR)
                .cachePublic();
        String anyPathByVersion = PREFIX_STATIC_RESOURCES + "/" + version.getVersion() + "/**";

        registry.addResourceHandler(anyPathByVersion)
                .addResourceLocations("classpath:/static/")
                .setCacheControl(cacheControl);
    }
}
