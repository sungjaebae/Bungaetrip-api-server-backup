//package GoGetters.GoGetter.config;
//
//import io.undertow.servlet.api.SecurityConstraint;
//import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
//import org.springframework.boot.web.server.WebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EmbeddedTomcatConfig implements WebServerFactoryCustomizer {
//
//    @Override
//    public void customize(WebServerFactory factory) {
//        TomcatContextCustomizer tomcatContextCustomizer = context -> {
//            SecurityConstraint securityConstraint = new SecurityConstraint();
//            securityConstraint.setDisplayName("Forbidden");
//            securityConstraint.setAuthConstraint(true);
//            SecurityCollection securityCollection = new SecurityCollection();
//            securityCollection.addPattern("/*");
//            securityCollection.addMethod("PUT");
//            securityCollection.addMethod("DELETE");
//            securityCollection.addMethod("TRACE");
//            securityCollection.addMethod("OPTIONS");
//            securityCollection.setName("Forbidden");
//            securityConstraint.addCollection(securityCollection);
//            context.addConstraint(securityConstraint);
//
//            Wrapper defaultServlet = (Wrapper) context.findChild("default");
//            defaultServlet.addInitParameter("readonly", "true");
//        };
//        factory.addContextCustomizers(tomcatContextCustomizer);
//    }
//}
