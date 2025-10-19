package com.company.urlshortener;

import com.company.urlshortener.model.Url;
import com.company.urlshortener.repository.UrlRepository;
import com.company.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class UrlServiceTest {

    @Test
    void shortenCreatesShortCode() {
        UrlRepository repo = Mockito.mock(UrlRepository.class);
        Url saved = new Url("http://example.com");
        saved.setShortCode(null);
        // mimic save assigning id
        Mockito.when(repo.save(any(Url.class))).thenAnswer(inv -> {
            Url arg = inv.getArgument(0);
            if (arg.getId() == null) {
                // assign id via reflection simulation - but simpler: set id using a setter is not available
                // So we simulate by returning an object with id populated
                Url u = new Url(arg.getLongUrl());
                java.lang.reflect.Field idField;
                try {
                    idField = Url.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(u, 123L);
                } catch (Exception e) {
                    // ignore
                }
                return u;
            }
            return arg;
        });

        var service = new UrlService(repo);
        var response = service.shortenUrl("http://example.com");
        assertNotNull(response.getShortCode());
        assertTrue(response.getShortCode().length() > 0);
    }
}
