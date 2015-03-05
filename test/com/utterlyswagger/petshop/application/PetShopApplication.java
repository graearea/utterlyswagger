package com.utterlyswagger.petshop.application;

import com.googlecode.utterlyidle.BasePath;
import com.googlecode.utterlyidle.RestApplication;
import com.utterlyswagger.SwaggerInfo;
import com.utterlyswagger.SwaggerModule;
import com.utterlyswagger.TargetEndpointBaseLocation;

import static com.googlecode.totallylazy.Option.some;

public class PetShopApplication extends RestApplication {

    public PetShopApplication(BasePath basePath) {
        super(basePath);

        add(new SwaggerModule(
            new SwaggerInfo("Swagger Petstore", "1.0.0")
                .description("This is a sample server Petstore server.  You can find out more about Swagger at <a href=\"http://swagger.io\">http://swagger.io</a> or on irc.freenode.net, #swagger.  For this sample, you can use the api key \"special-key\" to test the authorization filters")
                .termsOfService("http://helloreverb.com/terms/")
                .contactEmail("apiteam@wordnik.com")
                .licenceName("Apache 2.0")
                .licenceUrl("http://www.apache.org/licenses/LICENSE-2.0.html"),
            new TargetEndpointBaseLocation(some("/v2"), some("petstore.swagger.io")),
            "/petshop/swagger"));
        add(new PetShopModule());
    }
}
