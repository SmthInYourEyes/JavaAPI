package testsWriting;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Ex12 {
    @Test
    public void headerTest() {
        //Узнаем что за header
        Response response = RestAssured
                .get(" https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers = response.getHeaders();
        System.out.println(headers);

        //Сравнивем полученный header с ожидаемым
        String header = response.getHeader("x-secret-homework-header");
        assertEquals("Some secret value", header);
    }
}