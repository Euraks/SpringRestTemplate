package org.example.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MySpringMvcDispatcherServletInitializerTest {

    private final MySpringMvcDispatcherSerlvetIntitializer initializer = new MySpringMvcDispatcherSerlvetIntitializer();

    @Test
    void getRootConfigClasses_ShouldReturnNull() {
        assertThat( initializer.getRootConfigClasses() ).isNull();
    }

    @Test
    void getServletConfigClasses_ShouldReturnAppConfig() {
        Class<?>[] configClasses = initializer.getServletConfigClasses();
        assertThat( configClasses ).containsExactly( AppConfig.class );
    }

    @Test
    void getServletMappings_ShouldReturnRootPath() {
        String[] mappings = initializer.getServletMappings();
        assertThat( mappings ).containsExactly( "/" );
    }
}
