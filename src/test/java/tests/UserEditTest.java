package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Edit user cases")
@Feature("Edit")
public class UserEditTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("Тест проверяет изменение имени у авторизированного пользователя")
    @DisplayName("Тест проверяет изменение имени у авторизированного пользователя")
    @Test
    public void testEditJustCreatedTest() {
        //Создаем пользователя
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/", userData).jsonPath();

        String userId = responseCreateAuth.getString("id");


        //Авторизируемся
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/login/", authData);

        String header = responseGetAuth.getHeader("x-csrf-token");
        String token = responseGetAuth.getCookie("auth_sid");


        //Изменяем имя
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makePutRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                editData,
                header,
                token
        );


        //Получаем данные пользователя
        Response responseUserData = apiCoreRequests.
                makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                        header,
                        token);

        Assertions.assertJsonByName(responseUserData, "firstName", newName);
    }

    @Description("Тест проверяет изменение имени у НЕавторизированного пользователя")
    @DisplayName("Тест проверяет изменение имени у НЕавторизированного пользователя")
    @Test
    public void testUnauthorizedEditJustCreatedUser() {
        //Создаем пользователя
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/", userData).jsonPath();

        String userId = responseCreateAuth.getString("id");


        //Изменяем имя
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makeNoAuthPutRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                editData);

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error","Auth token not supplied");
    }

    @Description("Тест проверяет изменение имени у авторизированного другого пользователя")
    @DisplayName("Тест проверяет изменение имени у авторизированного другого пользователя")
    @Test
    public void testAnotherUserAuthorizeEditJustCreatedUser() {
        //Создаем пользователя
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/", userData).jsonPath();

        String userId = responseCreateAuth.getString("id");


        //Авторизируемся под другим пользователем
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("vinkotov@example.com"));
        authData.put("password", userData.get("1234"));

        Response responseGetAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/login/", authData);

        String header = responseGetAuth.getHeader("x-csrf-token");
        String token = responseGetAuth.getCookie("auth_sid");


        //Изменяем имя
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makePutRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                editData,
                header,
                token
        );

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error","Auth token not supplied");
    }

    @Description("Тест проверяет изменение email на невалидный")
    @DisplayName("Тест проверяет изменение email на невалидный")
    @Test
    public void testEditWrongEmailJustCreated() {
        //Создаем пользователя
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/", userData).jsonPath();

        String userId = responseCreateAuth.getString("id");


        //Авторизируемся
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/login/", authData);

        String header = responseGetAuth.getHeader("x-csrf-token");
        String token = responseGetAuth.getCookie("auth_sid");


        //Изменяем имя
        String newEmail = "aaayandex.ru";
        Map<String, String> editData = new HashMap<>();
        editData.put("email", newEmail);

        Response responseEditUser = apiCoreRequests.makePutRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                editData,
                header,
                token
        );

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error","Invalid email format");
    }


    @Description("Тест проверяет изменение имени на невалидное")
    @DisplayName("Тест проверяет изменение имени на невалидное")
    @Test
    public void testEditWrongNameJustCreatedUser() {
        //Создаем пользователя
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/", userData).jsonPath();

        String userId = responseCreateAuth.getString("id");


        //Авторизируемся
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.
                makePostRequest("https://playground.learnqa.ru/api/user/login/", authData);

        String header = responseGetAuth.getHeader("x-csrf-token");
        String token = responseGetAuth.getCookie("auth_sid");


        //Изменяем имя
        String newName = "Ы";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makePutRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                editData,
                header,
                token
        );

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error","The value for field `firstName` is too short");
    }

}
