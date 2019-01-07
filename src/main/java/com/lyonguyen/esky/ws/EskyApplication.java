package com.lyonguyen.esky.ws;

import com.lyonguyen.esky.ws.providers.ApplicationBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class EskyApplication extends ResourceConfig {
    public EskyApplication() {
        register(new ApplicationBinder());
        packages("com.lyonguyen.esky.ws.resources");
    }
}
