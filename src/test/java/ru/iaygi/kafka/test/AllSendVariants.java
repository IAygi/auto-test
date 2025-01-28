package ru.iaygi.kafka.test;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class AllSendVariants {

    private static final String KAFKA_PATH = "localhost:29092";
    private static final String TOPIC = "test-topic";

    private Properties producerProps() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_PATH);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

    private Properties consumerProps() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_PATH);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Test
    void testFireAndForget() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps());

        String key = "fire-and-forget-key";
        String value = "fire-and-forget-value";

        // Fire-and-forget send
        producer.send(new ProducerRecord<>(TOPIC, key, value));
        producer.close();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps());
        consumer.subscribe(Collections.singletonList(TOPIC));

        ConsumerRecord<String, String> record = null;
        for (ConsumerRecord<String, String> r : consumer.poll(Duration.ofSeconds(5))) {
            record = r;
        }

        consumer.close();

        assert record != null;
        assertThat(TOPIC).isEqualTo(record.topic());
        assertThat(key).isEqualTo(record.key());
        assertThat(value).isEqualTo(record.value());
    }

    @Test
    void testSynchronousSend() throws Exception {
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps());

        String key = "sync-key";
        String value = "sync-value";

        // Synchronous send
        producer.send(new ProducerRecord<>(TOPIC, key, value)).get();
        producer.close();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps());
        consumer.subscribe(Collections.singletonList(TOPIC));

        ConsumerRecord<String, String> record = null;
        for (ConsumerRecord<String, String> r : consumer.poll(Duration.ofSeconds(5))) {
            record = r;
        }

        consumer.close();

        assert record != null;
        assertThat(TOPIC).isEqualTo(record.topic());
        assertThat(key).isEqualTo(record.key());
        assertThat(value).isEqualTo(record.value());
    }

    @Test
    void testAsynchronousSend() throws InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps());

        String key = "async-key";
        String value = "async-value";

        final boolean[] callbackTriggered = {false};

        // Asynchronous send with callback
        producer.send(new ProducerRecord<>(TOPIC, key, value), (metadata, exception) -> {
            assertThat(exception).isNull();
            assertThat(metadata.topic()).isEqualTo(TOPIC);
            callbackTriggered[0] = true;
        });

        // Wait for callback to complete
        Thread.sleep(1000);
        producer.close();

        assertThat(callbackTriggered[0]).isTrue();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps());
        consumer.subscribe(Collections.singletonList(TOPIC));

        ConsumerRecord<String, String> record = null;
        for (ConsumerRecord<String, String> r : consumer.poll(Duration.ofSeconds(5))) {
            record = r;
        }

        consumer.close();

        assert record != null;
        assertThat(TOPIC).isEqualTo(record.topic());
        assertThat(key).isEqualTo(record.key());
        assertThat(value).isEqualTo(record.value());
    }
}
