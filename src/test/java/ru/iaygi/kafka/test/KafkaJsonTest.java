package ru.iaygi.kafka.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.iaygi.kafka.data.JsonMessage;
import ru.iaygi.kafka.util.KafkaConsumerConfig;
import ru.iaygi.kafka.util.KafkaProducerConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iaygi.common.FakeData.numbersCollection;
import static ru.iaygi.enums.KafkaEnum.*;

public class KafkaJsonTest {

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;
    private KafkaConsumer<String, String> consumer2;
    private final String bootstrapServers = BOOTSTRAP_LOCAL_SERVER.getValue();
    private final String topic = TEST_TOPIC.getValue();

    @BeforeEach
    void setUp() {
        // Создаем продюсер и консюмер
        producer = KafkaProducerConfig.createProducer(bootstrapServers);
        consumer = KafkaConsumerConfig.createConsumer(bootstrapServers, topic, TEST_GROUP.getValue());
        consumer.subscribe(List.of(topic));
        consumer2 = KafkaConsumerConfig.createConsumer(bootstrapServers, topic, NEW_TEST_GROUP.getValue());
        consumer2.subscribe(List.of(topic));
        System.out.println();
    }

    @AfterEach
    void tearDown() {
        // Закрываем продюсер и консюмер
        producer.close();
        consumer.close();
        consumer2.close();
    }

    @Test
    void testSendAndReceiveJson() throws Exception {
        String jsonMessage = JsonMessage.createJsonMessage();

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key-" + numbersCollection(4),
                jsonMessage);
        producer.send(record).get(); // Используем `.get()` для синхронной отправки

        step("Первый консьюмер", () -> {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(15));
            List<ConsumerRecord<String, String>> recordList = new ArrayList<>();
            records.records(topic).forEach(recordList::add);

            assertThat(recordList.get(0).value()).contains(jsonMessage);
        });

        step("Второй консьюмер", () -> {
            ConsumerRecords<String, String> records2 = consumer2.poll(Duration.ofSeconds(15));
            List<ConsumerRecord<String, String>> recordList2 = new ArrayList<>();
            records2.records(topic).forEach(recordList2::add);

            assertThat(recordList2.get(0).value()).contains(jsonMessage);
        });
    }
}
