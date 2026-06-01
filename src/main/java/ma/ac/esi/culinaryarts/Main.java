package ma.ac.esi.culinaryarts;

import java.io.File;

import ma.ac.esi.culinaryarts.controller.*;
import jakarta.servlet.MultipartConfigElement;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class Main {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8090);
        tomcat.getConnector();

        String webappDir = new File("src/main/webapp").getAbsolutePath();

        Context ctx = tomcat.addWebapp("/culinaryarts", webappDir);
        ctx.setParentClassLoader(Main.class.getClassLoader());

        /*
         * RecipeController
         */
        Tomcat.addServlet(
                ctx,
                "RecipeController",
                new RecipeController()
        ).setLoadOnStartup(1);

        ctx.addServletMappingDecoded(
                "/recipes",
                "RecipeController"
        );

        /*
         * LoginController
         */
        Tomcat.addServlet(
                ctx,
                "LoginController",
                new LoginController()
        ).setLoadOnStartup(1);

        ctx.addServletMappingDecoded(
                "/LoginController",
                "LoginController"
        );

        /*
         * RecipeSubmitController
         * IMPORTANT : activation du multipart/form-data
         */
        var recipeSubmitServlet = Tomcat.addServlet(
                ctx,
                "RecipeSubmitController",
                new RecipeSubmitController()
        );

        recipeSubmitServlet.setLoadOnStartup(1);

        recipeSubmitServlet.setMultipartConfigElement(
                new MultipartConfigElement(
                        System.getProperty("java.io.tmpdir")
                )
        );

        ctx.addServletMappingDecoded(
                "/RecipeSubmitController",
                "RecipeSubmitController"
        );

        /*
         * LogoutController
         */
        Tomcat.addServlet(
                ctx,
                "LogoutController",
                new LogoutController()
        ).setLoadOnStartup(1);

        ctx.addServletMappingDecoded(
                "/LogoutController",
                "LogoutController"
        );

        tomcat.start();

        System.out.println(
                "L'application est lancée sur : http://localhost:8090/culinaryarts/"
        );

        tomcat.getServer().await();
    }
}