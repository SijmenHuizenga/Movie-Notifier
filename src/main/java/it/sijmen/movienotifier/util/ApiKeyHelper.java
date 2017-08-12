package it.sijmen.movienotifier.util;

import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import java.util.Random;

@Service
@Singleton
public class ApiKeyHelper {

    private final char[] symbols;

    private final Random random;

    public ApiKeyHelper() {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();

        random = new Random();
    }

    public String randomAPIKey() {
        char[] buf = new char[64];

        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

}
