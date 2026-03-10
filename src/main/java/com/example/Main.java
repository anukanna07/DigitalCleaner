package com.example;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

import servlet.RegisterServlet;
import servlet.LoginServlet;
import servlet.CleanServlet;
import servlet.ScanServlet;
import servlet.LogoutServlet;
import servlet.DeletePermanentServlet;

public class Main {

    public static void main(String[] args) throws Exception {

        String port = System.getenv("PORT");
        if (port == null) {
            port = "8080";
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.getConnector();

        String webappDir = "src/main/webapp";
        Context ctx = tomcat.addWebapp("", new File(webappDir).getAbsolutePath());

        // Register servlets
        Tomcat.addServlet(ctx, "RegisterServlet", new RegisterServlet());
        ctx.addServletMappingDecoded("/RegisterServlet", "RegisterServlet");

        Tomcat.addServlet(ctx, "LoginServlet", new LoginServlet());
        ctx.addServletMappingDecoded("/LoginServlet", "LoginServlet");

        Tomcat.addServlet(ctx, "CleanServlet", new CleanServlet());
        ctx.addServletMappingDecoded("/CleanServlet", "CleanServlet");

        Tomcat.addServlet(ctx, "ScanServlet", new ScanServlet());
        ctx.addServletMappingDecoded("/ScanServlet", "ScanServlet");

        Tomcat.addServlet(ctx, "LogoutServlet", new LogoutServlet());
        ctx.addServletMappingDecoded("/LogoutServlet", "LogoutServlet");

        Tomcat.addServlet(ctx, "DeletePermanentServlet", new DeletePermanentServlet());
        ctx.addServletMappingDecoded("/DeletePermanentServlet", "DeletePermanentServlet");

        System.out.println("Tomcat started on port: " + port);

        tomcat.start();
        tomcat.getServer().await();
    }
}

