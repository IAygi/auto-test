package ru.iaygi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaEnum {

    BOOTSTRAP_LOCAL_SERVER("localhost:29092"),
    TEST_TOPIC("test-topic"),
    NEW_TEST_TOPIC("new-test-topic"),
    TEST_GROUP("test-group"),
    NEW_TEST_GROUP("new-test-group");

    private final String value;
}
                                                                