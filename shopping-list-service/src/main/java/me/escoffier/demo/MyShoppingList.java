package me.escoffier.demo;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.circuitbreaker.CircuitBreaker;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;
import io.vertx.reactivex.servicediscovery.ServiceDiscovery;

public class MyShoppingList extends AbstractVerticle {

  private WebClient shopping, pricer;
  private CircuitBreaker circuit;

  @Override
  public void start() {

    Router router = Router.router(vertx);
    router.route("/health").handler(rc -> rc.response().end("OK"));
    router.route("/").handler(this::getShoppingList);

    ServiceDiscovery.create(vertx, discovery -> {
      // Get pricer-service

      // Get shopping-backend

      // Assigned the field and start the HTTP server When both have been retrieved

      vertx.createHttpServer()
        .requestHandler(router::accept)
        .listen(8080);
    });

  }

  private void getShoppingList(RoutingContext rc) {
    HttpServerResponse serverResponse =
      rc.response().setChunked(true);

       /*
         +--> Retrieve shopping list
           +
           +-->  for each item, call the pricer, concurrently
                    +
                    |
                    +-->  For each completed evaluation (line),
                          write it to the HTTP response
         */

    Single<JsonObject> single = shopping.get("/shopping")
      .rxSend()
      .map(HttpResponse::bodyAsJsonObject);
       
         /*
                               shopping          pricer
                               backend
                 +                +                +
                 |                |                |
                 +--------------> |                |
                 |                |                |
                 |                |                |
                 +-------------------------------> |
                 |                |                |
                 +-------------------------------> |
        write <--|                |                |
                 +-------------------------------> |
        write <--|                +                +
                 |
        write <--|
                 |
          end <--|
         */


  }

}
