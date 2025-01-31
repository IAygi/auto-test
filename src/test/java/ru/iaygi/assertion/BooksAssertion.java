package ru.iaygi.assertion;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.iaygi.dto.book.BooksItem;
import ru.iaygi.dto.book.Response;

import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class BooksAssertion {

    public void checkResponse(Response response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BooksItem template = objectMapper.readValue(Paths.get("src/test/java/ru/iaygi/api/data/json/books.json")
                .toFile(), BooksItem.class);

        assertThat(response.getBooks().toArray()).extracting("isbn", "title", "subTitle", "author",
                        "publishDate", "publisher", "pages", "description", "website")
                .contains(tuple(template.getIsbn(), template.getTitle(), template.getSubTitle(),
                        template.getAuthor(), template.getPublishDate(), template.getPublisher(),
                        template.getPages(), template.getDescription(), template.getWebsite()));

        assertThat(response.getBooks().toArray()).contains(template);

        assertThat(response.getBooks().get(response.getBooks().size() - 1)).isEqualTo(template);
    }
}
