import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class Ex5 {
    @Test
    public void jsonParsing() {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        //Выводим JSON
        response.prettyPrint();

        //Выводим текст второго сообщения
        String secondMessage = response.get("messages[1].message");
        System.out.println(secondMessage);
    }
}