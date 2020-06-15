package gpse.repoll;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures Spring to handle all HTTP Requests that are not prefixed by /api/v1/
 * by serving the static html, js, css files.
 *
 * '@RequestMapping' Annotations have higher priority than this, so they are not overridden
 * by this config.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/{spring:\\w+}")
            .setViewName("forward:/");
        registry.addViewController("/**/{spring:\\w+}")
            .setViewName("forward:/");
        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css)$}")
            .setViewName("forward:/");
    }
}
