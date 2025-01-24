package ru.iaygi.api.helper;

import org.junit.jupiter.params.provider.Arguments;
import ru.iaygi.api.rest.AttachmentRest;
import ru.iaygi.api.service.AttachmentCreation;

import java.util.stream.Stream;

public abstract class AttachmentHelper extends BaseHelper {

    protected AttachmentCreation attachmentCreation = new AttachmentCreation();
    protected AttachmentRest attachmentRest = new AttachmentRest();

    protected static Stream<Arguments> types() {
        return Stream.of(
                Arguments.of("", "")
        );
    }
}