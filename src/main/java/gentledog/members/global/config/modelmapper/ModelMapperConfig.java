package gentledog.members.global.config.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)  // private 필드에도 접근하여 매핑할 수 있도록 허용
                .setMatchingStrategy(MatchingStrategies.STRICT)  // 필드명이 완전히 일치하는 경우에만 매핑을 수행
                .setFieldMatchingEnabled(true); // 필드 매칭 활성화(안하면 매핑 안됨)
        return modelMapper;
    }
}
