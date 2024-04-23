import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex7 {
    @Test
    public void longRedirect() {
        Response response = null;
        String locationHeader = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode = 0;

        do {
            response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(locationHeader)
                    .andReturn();
            statusCode = response.getStatusCode();
            if (statusCode == 200) {
                break;
            }
            locationHeader = response.getHeader("Location");
        } while (statusCode != 200);

        System.out.println(locationHeader);
        System.out.println(statusCode);
    }
}
