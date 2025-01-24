package ru.iaygi.kafka.test;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import ru.iaygi.annotation.Note;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.iaygi.common.FakeData.harryPotterBook;
import static ru.iaygi.common.FakeData.harryPotterLocation;

@Note("Для запуска теста надо запустить локальный Docker, образ Kafka устанавливать не нужно")
public class KafkaTest {

    private final static String KAFKA_PATH = "PLAINTEXT://localhost:29092";
    private KafkaContainer kafkaContainer;

    @BeforeEach
    public void init() {
//        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));
//        kafkaContainer.start();
    }

    @AfterEach
    public void clear() {
//        kafkaContainer.stop();
    }

    @Test
    void testKafkaSendAndReceive() {
        String bootstrapServers = KAFKA_PATH;
        String topic = "new-test-topic";

        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

        String key = harryPotterLocation();
        String value = harryPotterBook();
        producer.send(new ProducerRecord<>(topic, key, value));
        producer.close();

        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(topic));

        ConsumerRecord<String, String> record = null;
        for (ConsumerRecord<String, String> r : consumer.poll(Duration.ofSeconds(5))) {
            record = r;
        }

        consumer.close();

        assert record != null;
        assertThat(topic).isEqualTo(record.topic());
        assertThat(key).isEqualTo(record.key());
        assertThat(value).isEqualTo(record.value());
    }
}
