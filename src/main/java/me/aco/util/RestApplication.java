package me.aco.util;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("api/v1")
public class RestApplication extends Application {
    // Needed to enable Jakarta REST and specify path.
}
