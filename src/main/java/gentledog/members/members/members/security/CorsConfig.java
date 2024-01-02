//package gentledog.members.global.config.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
////@EnableWebMvc // 여기에는 필요없음 -> 웹 요청 및 응답을 처리하는데 필요 ex) 컨트롤러, 뷰 리졸버, 핸들러 매핑, 인터셉터 등을 활성화 함
//public class CorsConfig implements WebMvcConfigurer {
//    // CORS 설정
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 모든 uri에 대해 http://localhost:3000 에서 오는 요청을 허용한다.
//        registry.addMapping("/**")
//                .allowedMethods(CorsConfiguration.ALL)
//                .allowedHeaders(CorsConfiguration.ALL)
//                .allowedOriginPatterns(CorsConfiguration.ALL)
//                .allowCredentials(true);
//    }
//}
//
