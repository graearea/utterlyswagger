package com.utterlyswagger.builder;

import com.googlecode.totallylazy.Option;
import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.Sequence;
import com.utterlyswagger.SwaggerInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.googlecode.totallylazy.Maps.map;
import static com.googlecode.totallylazy.Maps.pairs;
import static com.googlecode.totallylazy.Option.none;
import static com.googlecode.totallylazy.Option.some;
import static com.googlecode.totallylazy.Pair.pair;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.utterlyswagger.builder.Operations.realiseMap;

public class SwaggerV1_2 {

    public static Map<String, Object> swaggerV1_2(SwaggerInfo info, Map<String, Sequence<Operation>> operations) {
        return realiseMap(
            pair("swaggerVersion", "1.2"),
            pair("info", swaggerInfo(info)),
            pair("apis", apis(operations)),
            pair("basePath", urlFor(info.host, info.basePath)),
            pair("apiVersion", info.apiVersion)
        );
    }

    public static Map<String, Object> swaggerInfo(SwaggerInfo info) {
        return realiseMap(
            pair("title", (Object) info.title),
            pair("description", info.description),
            pair("termsOfServiceUrl", info.termsOfService),
            pair("contact", info.contactEmail),
            pair("license", info.licenceName),
            pair("licenseUrl", info.licenceUrl));
    }

    public static Sequence<Map<String, Object>> apis(Map<String, Sequence<Operation>> resources) {
        return pairs(resources).map(SwaggerV1_2::apiObject);
    }


    private static Map<String, Object> apiObject(Pair<String, Sequence<Operation>> pair) {
        return map(
            "path", pair.first(),
            "operations", pair.second().map(SwaggerV1_2::operationObject));
    }

    private static Map<String, Object> operationObject(Operation operation) {
        return map(sequence(
            pair("method", (Object) operation.method.toUpperCase()),
            pair("nickname", operation.javaMethodName),
            pair("notes", operation.description),
            pair("summary", operation.summary),
            pair("produces", operation.produces),
            pair("parameters", parameters(operation)),
            pair("responseMessages", responses(operation))
        ));
    }

    private static Sequence<Map<String, Object>> parameters(Operation operation) {
        return operation.parameters.map(SwaggerV1_2::parameter);
    }

    private static Map<String, String> paramLocation = map(sequence(
        pair("body", "body"),
        pair("PathParameters", "path"),
        pair("CookieParameters", "cookie"),
        pair("QueryParameters", "query"),
        pair("FormParameters", "form"),
        pair("HeaderParameters", "header")
    ));

    private static Map<String, Object> parameter(Parameter param) {
        return realiseMap(
            pair("name", param.name),
            pair("paramType", paramLocation.getOrDefault(
                param.paramType, "unknown")),
            pair("required", param.required),
            pair("type", param.type),
            pair("description", param.description)
        );
    }

    private static Sequence<Map<String, Object>> responses(Operation operation) {
        return operation.responses
            .filter(desc -> !desc.status().equals("default"))
            .map(desc ->
                map("code", (Object) Integer.parseInt(desc.status()),
                    "message", desc.description()));
    }

    private static Option<String> urlFor(Option<String> host, Option<String> basePath) {
        try {
            return some(new URL("http", host.getOrElse(""), basePath.getOrElse("")).toString());
        } catch (MalformedURLException e) {
            return none();
        }
    }

}
