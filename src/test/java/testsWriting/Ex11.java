package testsWriting;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Ex11 {
    @Test
    public void cookieTest() {
        //Узнаем что за cookie
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String,String> cookies = response.getCookies();
        System.out.println(cookies);

        //Сравнивем полученный cookie с ожидаемым
        String cookie = response.getCookie("HomeWork");
        assertEquals("hw_value", cookie);
    }
}