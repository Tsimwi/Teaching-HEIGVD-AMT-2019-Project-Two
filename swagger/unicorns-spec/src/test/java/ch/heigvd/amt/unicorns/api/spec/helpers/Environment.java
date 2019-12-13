package ch.heigvd.amt.unicorns.api.spec.helpers;

import ch.heigvd.amt.unicorns.api.DefaultApiTest;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private DefaultApiTest api = new DefaultApiTest();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heigvd.amt.unicorns.server.url");
        api.getApiClient().setBasePath(url);

    }

    public DefaultApiTest getApi() {
        return api;
    }


}
