package com.gazorpazorp.OrderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.gazorpazorp.client.config.CustomOAuth2FeignRequestInterceptor;
import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication(scanBasePackages="com.gazorpazorp")
//@ComponentScan(basePackages="com.gazorpazorp", excludeFilters = @Filter(type = FilterType.REGEX, pattern = "com.gazorpazorp.clientConfig.*"))
@EnableJpaRepositories("com.gazorpazorp.repository")
@EntityScan(basePackages="com.gazorpazorp")
@EnableEurekaClient
@EnableFeignClients("com.gazorpazorp.client")
@EnableResourceServer
@EnableOAuth2Client
//@EnableHystrix
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OrderAndDeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderAndDeliveryServiceApplication.class, args);
	}

	@Bean
	feign.RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext context) {
		if (context == null) return null;
		return new CustomOAuth2FeignRequestInterceptor(context);
	}
	
//	@PostConstruct
//	public void getDbManager(){
//	   DatabaseManagerSwing.main(
//		new String[] { "--url", "jdbc:hsqldb:mem:test://localhost/test?characterEncoding=UTF-8", "--user", "SA", "--password", ""});
//	}

	
	//TODO: RE-IMPLEMENT THIS SO THAT WE DON'T NEED TO HARD CODE THE ACCESS-TOKEN-URI IN THE YAML OAUTH CLIENT CONFIG
//	@Bean
//	UserInfoRestTemplateCustomizer oauth2RestTemplateCustomizer(RetryLoadBalancerInterceptor interceptor) {
//	    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//	    interceptors.add(interceptor);
//	    return template -> {
//	        AccessTokenProviderChain accessTokenProviderChain = Stream
//	                .of(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
//	                        new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())
//	                .peek(tp -> tp.setInterceptors(interceptors))
//	                .collect(Collectors.collectingAndThen(Collectors.toList(), AccessTokenProviderChain::new));
//	        template.setAccessTokenProvider(accessTokenProviderChain);
//	    };
//	}
	
	@Bean
	@Profile("!dev")
	public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils utils) 
	{
		EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(utils);
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
		instance.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		instance.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		instance.setDataCenterInfo(info);
		instance.setNonSecurePort(8080);
		return instance;
	}
}
