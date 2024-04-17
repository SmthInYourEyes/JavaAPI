package testsWriting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex10 {
    @Test
    public void shortPhraseTest() {
        // Объявляем строку
        String somePhrase = "Мама мыла раму";

        // Выводим длину строки и сравниваем ее с 15
        System.out.println(somePhrase.length());
        assertTrue(somePhrase.length() > 15);
    }
}
