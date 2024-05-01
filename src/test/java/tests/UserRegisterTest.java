package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.HashMap;
import java.util.Map;

import static lib.DataGenerator.getRegistrationData;

@Epic("Register cases")
@Feature("Register")
public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("Тест проверяет невозможность создания пользователя с существующим email")
    @DisplayName("Тест проверяет невозможность создания пользователя с существующим email")
    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }

    @Description("Тест успешного создания пользователя")
    @DisplayName("Тест успешного создания пользователя")
    @Test
    public void testCreateUserSuccessfully() {
        Map<String, String> userData = getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }

    @Description("Тест проверяет невозможность создания пользователя с некорректным email")
    @DisplayName("Тест проверяет невозможность создания пользователя с некорректным email")
    @Test
    public void testCreateUserWithIncorrectEmail() {
        String email = "vinkotovexample.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }


    @Description("Тест проверяет невозможность создания пользователя с одним из пустых полей")
    @DisplayName("Тест проверяет невозможность создания пользователя с одним из пустых полей")
    @ParameterizedTest
    @CsvSource({
            "email",
            "password",
            "firstName",
            "lastName",
            "username"
    })

    public void testCreateUserWithOneIncorrectField(String emptyValue) {
        Map<String, String> nonDefaultValues = new HashMap<>();
        nonDefaultValues.put(emptyValue, "");

        Map<String, String> userData = getRegistrationData(nonDefaultValues);
        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of '"+ emptyValue +"' field is too short");
    }



    @Description("Тест проверяет невозможность создания пользователя с коротким именем")
    @DisplayName("Тест проверяет невозможность создания пользователя с коротким именем")
    @Test
    public void testCreateUserWithShortName() {
        String firstName = "a";

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
    }

    @Description("Тест проверяет невозможность создания пользователя с длинным именем")
    @DisplayName("Тест проверяет невозможность создания пользователя с длинным именем")
    @Test
    public void testCreateUserWithLongName() {
        String firstName = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

        Map<String, String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too long");
    }
}
