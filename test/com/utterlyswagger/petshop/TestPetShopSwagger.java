package com.utterlyswagger.petshop;

import org.junit.Test;

import java.util.Map;

import static com.utterlyswagger.petshop.path.PathAssertions.mapInPathKeys;
import static com.utterlyswagger.petshop.path.PathAssertions.stringInPath;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

public abstract class TestPetShopSwagger {

    @Test
    public void definesSwaggerVersion() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("2.0"), "swagger")
//                stringInPath(is("/v2"), "basePath"),
//                stringInPath(is("petstore.swagger.io"), "host")
            ));
    }

    @Test
    public void definesMinimumSwaggerInfo() throws Exception {
        assertThat(
            getSwagger(),
            allOf(
                stringInPath(is("Swagger Petstore"), "info", "title"),
                stringInPath(is("1.0.0"), "info", "version")
//                stringInPath(is("This is a sample server Petstore server.  You can find out more about Swagger at <a href=\"http://swagger.io\">http://swagger.io</a> or on irc.freenode.net, #swagger.  For this sample, you can use the api key \"special-key\" to test the authorization filters"), "info", "description"),
//                stringInPath(is("http://helloreverb.com/terms/"), "info", "termsOfService"),
//                stringInPath(is("apiteam@wordnik.com"), "info", "contact", "email"),
//                stringInPath(is("Apache 2.0"), "info", "license", "name"),
//                stringInPath(is("http://www.apache.org/licenses/LICENSE-2.0.html"), "info", "license", "url")
            ));
    }

    @Test
    public void definesPaths() throws Exception {
        assertThat(
            getSwagger(),
            mapInPathKeys(hasItems(
                    "/pet", "/pet/findByStatus", "/pet/findByTags",
                    "/pet/{petId}", "/pet/{petId}/uploadImage", "/store/inventory",
                    "/store/order", "/store/order/{orderId}", "/user",
                    "/user/createWithArray", "/user/createWithList", "/user/login",
                    "/user/logout", "/user/{username}"),
                "paths")
        );
    }

    protected abstract Map<String, Object> getSwagger() throws Exception;
}
