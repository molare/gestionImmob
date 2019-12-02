package com.immo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class GestionImmobApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionImmobApplication.class, args);
		/*try {
			openHomePages();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	/*private static void openHomePages() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8082/login");
	}*/
}
