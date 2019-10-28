package com.immo;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionImmobApplication {

	/*private static final int HTTP_PORT = 80;
	private static final int HTTPS_PORT = 443;
	private static final String HTTP = "http";
	private static final String USER_CONSTRAINT = "CONFIDENTIAL";
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint(USER_CONSTRAINT);
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("*//*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	private Connector redirectConnector() {
		Connector connector = new Connector(
				TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme(HTTP);
		connector.setPort(HTTP_PORT);
		connector.setSecure(false);
		connector.setRedirectPort(HTTPS_PORT);
		return connector;
	}*/

	public static void main(String[] args) {
		SpringApplication.run(GestionImmobApplication.class, args);
	}

}