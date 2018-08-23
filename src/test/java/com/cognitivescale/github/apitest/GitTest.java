package com.cognitivescale.github.apitest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


/**
 * Created by VinayKarumuri on 8/22/18.
 */
public class GitTest {

  static Logger log = Logger.getLogger(GitTest.class);
  public String userName = "vinaykarumuri15";
  public String requestBaseURL = "https://api.github.com";

  @Test
  public void testGetSingleValidUser(){

    //Constructing URL
    String requestURL = requestBaseURL+"/users/"+userName;
    log.info(requestURL);

    //Extracting Response after firing request
      Response response=
          given()
              .header("Content-Type", "application/json")
              .auth()
              .basic(userName, "password")
              .get(requestURL)
              .then()
              .extract()
              .response();

    //Printing  response
    log.info(response.asString());
    log.info("response" + response.statusCode());


    //Validating response code
    Assert.assertTrue(response.statusCode()==200);

    //JSON response Pay load validations
    response.then().body("login",is(userName));
    response.then().body("containsKey('id')", is(true));
    response.then().body("containsKey('node_id')", is(true));
    response.then().body("containsKey('avatar_url')", is(true));
    response.then().body("containsKey('url')", is(true));
    response.then().body("containsKey('html_url')", is(true));
    response.then().body("containsKey('followers_url')", is(true));
    response.then().body("containsKey('following_url')", is(true));
    response.then().body("containsKey('gists_url')", is(true));
    response.then().body("containsKey('starred_url')", is(true));
    response.then().body("containsKey('subscriptions_url')", is(true));
    response.then().body("containsKey('organizations_url')", is(true));
    response.then().body("containsKey('repos_url')", is(true));
    response.then().body("containsKey('events_url')", is(true));
    response.then().body("containsKey('received_events_url')", is(true));
    response.then().body("type", is("User"));
    response.then().body("containsKey('site_admin')", is(true));
    response.then().body("containsKey('name')", is(true));
    response.then().body("containsKey('company')", is(true));
    response.then().body("containsKey('blog')", is(true));
    response.then().body("containsKey('location')", is(true));
    response.then().body("containsKey('email')", is(true));
    response.then().body("containsKey('hireable')", is(true));
    response.then().body("containsKey('bio')", is(true));
    response.then().body("containsKey('public_repos')", is(true));
    response.then().body("containsKey('public_gists')", is(true));
    response.then().body("containsKey('followers')", is(true));
    response.then().body("containsKey('following')", is(true));
    response.then().body("containsKey('created_at')", is(true));
    response.then().body("containsKey('updated_at')", is(true));
  }



  @Test
  public void testGetSingleInValidUser() {

    //Constructing URL
    String requestURL = requestBaseURL + "/users/" + "vkarumuri";
    log.info(requestURL);

    //Extracting Response after firing request
    Response response =
        given()
            //.header("Content-Type", "application/json")
            .auth()
            .basic("vkarumuri", "password")
            .get("https://api.github.com/users/vkarumuri")
            .then()
            .extract()
            .response();

    //Printing  response
    log.info(response.asString());
    log.info("response" + response.statusCode());


    //Validating response code
    Assert.assertTrue(response.statusCode() == 404);

    //JSON response Pay load validations
    response.then().body("message", is("Not Found"));
    response.then().body("documentation_url", is("https://developer.github.com/v3/users/#get-a-single-user"));
  }
  }
