import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Ex9 {
    @Test
    public void passSelection() {
        String login = "super_admin";
        List<String> passwords = Arrays.asList(
                "123456", "123456789", "qwerty", "password", "1234567",
                "12345678", "12345", "iloveyou", "111111", "123123",
                "abc123", "qwerty123", "1q2w3e4r", "admin", "qwertyuiop",
                "654321", "555555", "lovely", "7777777", "welcome",
                "888888", "princess", "dragon", "password1", "123qwe"
        );

            // Перебираем пароли и получаем Cookie
        for (String password : passwords) {
            Response response = RestAssured
                    .given()
                    .formParam("login", login)
                    .formParam("password", password)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            String authCookie = response.getCookie("auth_cookie");

            //Проверяем полученные Cookie, выводим правильный пароль
            response = RestAssured
                    .given()
                    .cookie("auth_cookie", authCookie)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            String check = response.asString();
            if (check.equals("You are authorized")) {
                System.out.println("Статус: " + check);
                System.out.println("Верный пароль: " + password);
            }
        }


    }

}

