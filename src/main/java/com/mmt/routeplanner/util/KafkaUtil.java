package com.mmt.routeplanner.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;

/** @author B0206734 */
@Slf4j
public class KafkaUtil {

  @Autowired private static ObjectMapper objectMapper = new ObjectMapper();

  private KafkaUtil() {}

  /**
   * Set up the properties for a consumer.
   *
   * @param bootstrapAddress the bootstrap address
   * @param groupId the group id
   * @param autoCommit the auto commit
   * @param maxPoll the max poll
   * @return the properties.
   */
  public static Map<String, Object> commonConsumerConfigs(
      String bootstrapAddress, String groupId, boolean autoCommit, OptionalInt maxPoll,String autoOffset) {

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffset);
    maxPoll.ifPresent(value -> props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, value));
    return props;
  }

  /**
   * Container factory concurrent kafka listener container factory.
   *
   * @param bootstrapAddress the bootstrap address
   * @param groupId the group id
   * @param autoCommit the auto commit
   * @param noOflisteners the no oflisteners
   * @param maxPoll the max poll
   * @return the concurrent kafka listener container factory
   */
  public static ConcurrentKafkaListenerContainerFactory<String, String> containerFactory(
      String bootstrapAddress,
      String groupId,
      boolean autoCommit,
      int noOflisteners,
      OptionalInt maxPoll,
      String autoOffset) {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(
        new DefaultKafkaConsumerFactory<>(
            commonConsumerConfigs(bootstrapAddress, groupId, autoCommit, maxPoll,autoOffset)));
    factory.setConcurrency(noOflisteners);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    return factory;
  }



  public static void commitOffset(Acknowledgment acknowledgment, MessageHeaders messageHeaders){
    try {
      acknowledgment.acknowledge();
      log.info("offset committed for key: {},topic: {}, partition: {},offset: {}", messageHeaders.get(
          RouteUtil.KEY),messageHeaders.get(RouteUtil.TOPIC), messageHeaders.get(RouteUtil.PARTITION_ID), messageHeaders.get(RouteUtil.OFFSET));
    } catch (CommitFailedException e) {
      log.error("Commit failed for for key: {}, partition: {},offset: {}", messageHeaders.get(RouteUtil.KEY),messageHeaders.get(RouteUtil.TOPIC), messageHeaders.get(RouteUtil.PARTITION_ID), messageHeaders.get(RouteUtil.OFFSET), e);
    }
  }

}
