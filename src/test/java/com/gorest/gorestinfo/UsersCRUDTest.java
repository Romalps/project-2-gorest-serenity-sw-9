package com.gorest.gorestinfo;

import com.gorest.goreststeps.UsersSteps;
import com.gorest.model.UsersPojo;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class UsersCRUDTest extends TestBase {


    static String name = "Prime" + TestUtils.getRandomValue();
    static String email = "prime" + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "Female";
    static String status = "active";
    static int usersId;

    @Steps
    UsersSteps usersSteps;

    @Title("This will create user")
    @Test
    public void test001() {

        ValidatableResponse response = usersSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);

        usersId = response.extract().path("id");
        HashMap<String, Object> userMap = response.extract().path("");
        Assert.assertThat(userMap, hasValue(usersId));

    }

    @Title("This will get the created user")
    @Test
    public void test002() {

        ValidatableResponse response = usersSteps.getTheUsersDetails(usersId);
        response.log().all().statusCode(200);
        HashMap<String, Object> userMap = response.extract().path("");
        Assert.assertThat(userMap, hasValue(usersId));
    }

    @Title("This will update the user")
    @Test
    public void test003() {
        name = "Kai" + TestUtils.getRandomValue();
        ValidatableResponse response = usersSteps.updateTheUsersDetails(usersId, name, email, gender, status);
        response.statusCode(200);
        HashMap<String, Object> userMap = response.extract().path("");
        Assert.assertThat(userMap, hasValue(usersId));
    }

    @Title("This will delete the user")
    @Test
    public void test004() {
        usersSteps.deleteTheUser(usersId)
                .statusCode(204);
        usersSteps.getTheUsersDetails(usersId)
                .statusCode(404);

    }


}
