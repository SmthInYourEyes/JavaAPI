import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex8 {
    @Test
    public void token() throws InterruptedException {
        // 1. Создаем задачу
        String url = "https://playground.learnqa.ru/ajax/api/longtime_job";
        JsonPath response = RestAssured
                .get(url)
                .jsonPath();
        String token = response.getString("token");
        int seconds = response.getInt("seconds");
        System.out.println("Токен: " + token);

        // 2. Делаем один запрос с token ДО того, как задача готова, убеждемся в правильности поля status
        JsonPath earlyResponse = RestAssured
                .given()
                .queryParam("token", token)
                .when()
                .get(url)
                .jsonPath();
        String earlyStatus = earlyResponse.getString("status");
        System.out.println("Статус: " + earlyStatus);

        // 3. Ждем нужное количество секунд
        Thread.sleep(seconds * 1000);

        // 4. Делаем один запрос c token ПОСЛЕ того, как задача готова, убеждаемся в правильности поля status и наличии поля result
        Response lateResponse = RestAssured
                .given()
                .queryParam("token", token)
                .when()
                .get(url)
                .andReturn();
        String lateStatus = lateResponse.jsonPath().getString("status");
        String result = lateResponse.jsonPath().getString("result");
        System.out.println("Статус: " + lateStatus);
        System.out.println("Поле результат: " + result);
    }
}
