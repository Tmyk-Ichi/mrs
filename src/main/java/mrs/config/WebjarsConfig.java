package mrs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "webjars")
@Data
public class WebjarsConfig {
	private String bootstrap;
	private String jquery;
	
	public String getBootstrap() {
		return bootstrap;
	}
	public void setBootstrap(String bootstrap) {
		this.bootstrap = bootstrap;
	}
	public String getJquery() {
		return jquery;
	}
	public void setJquery(String jquery) {
		this.jquery = jquery;
	}
	
	

}
