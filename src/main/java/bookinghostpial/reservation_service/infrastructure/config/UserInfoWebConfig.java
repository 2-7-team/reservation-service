package bookinghostpial.reservation_service.infrastructure.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import bookinghospital.common_module.userInfo.resolver.UserInfoArgumentResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserInfoWebConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new UserInfoArgumentResolver());
	}
}

