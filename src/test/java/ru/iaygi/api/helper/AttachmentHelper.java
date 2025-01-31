package ru.iaygi.api.helper;

import org.junit.jupiter.params.provider.Arguments;
import ru.iaygi.api.rest.AttachmentRest;
import ru.iaygi.assertion.BooksAssertion;

import java.util.stream.Stream;

public abstract class AttachmentHelper extends BaseHelper {

    protected AttachmentRest attachmentRest = new AttachmentRest();
    protected BooksAssertion booksAssertion = new BooksAssertion();

    protected static Stream<Arguments> types() {
        return Stream.of(
                Arguments.of("", "")
        );
    }
}