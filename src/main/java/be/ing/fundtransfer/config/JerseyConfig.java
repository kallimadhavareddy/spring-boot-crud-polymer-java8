package be.ing.fundtransfer.config;

import be.ing.fundtransfer.controller.LoginController;
import be.ing.fundtransfer.controller.MailController;
import be.ing.fundtransfer.controller.SendMessageController;
import be.ing.fundtransfer.controller.TransactionController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        register(RequestContextFilter.class);
        register(WadlResource.class);
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(LoginController.class);
        register(MailController.class);
        register(SendMessageController.class);
        register(TransactionController.class);

    }
}