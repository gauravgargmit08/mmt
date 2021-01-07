package com.mmt.routeplanner.config;


import com.mmt.routeplanner.util.KafkaUtil;
import java.util.OptionalInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

/**
 * The type Retry consumer config.
 */
@Configuration
@EnableKafka
@Slf4j
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.bus.event-group.id}")
    private String busEventsId;

    @Value(value = "${kafka.flight.event-group.id}")
    private String flightEventsId;

    @Value(value = "${kafka.record.max.poll}")
    private int maxPoll;

    @Value(value = "${kafka.auto.offset.reset}")
    private String autoOffset;

    @Value(value = "${kafka.numOfKafkaListeners}")
    private int noOflisteners;


    /**
     * The bean ConcurrentKafkaListenerContainerFactory is use to create the message
     * listener container responsible to serve this endpoint.
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerTxnContainerFactoryFlight() {
        return KafkaUtil.containerFactory(bootstrapAddress,busEventsId,false,noOflisteners,OptionalInt.of(maxPoll),autoOffset);
    }

    /**
     * The bean ConcurrentKafkaListenerContainerFactory is use to create the message
     * listener container responsible to serve this endpoint.
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerTxnContainerFactoryBus() {
        return KafkaUtil.containerFactory(bootstrapAddress,flightEventsId,false,noOflisteners,OptionalInt.of(maxPoll),autoOffset);
    }




}