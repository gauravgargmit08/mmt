package com.mmt.routeplanner.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmt.routeplanner.model.BusEvent;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.service.EventRouter;
import com.mmt.routeplanner.util.KafkaUtil;
import com.mmt.routeplanner.util.RouteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * The type Batch consumer.
 */
@Service
@Slf4j
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class EventsConsumer {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EventRouter eventRouter;

	/**
	 *
	 * @param payload
	 * @param messageHeaders
	 * @param acknowledgment
	 */
	@KafkaListener(topics = "${kafka.bus.event-topic}", containerFactory = "kafkaListenerTxnContainerFactoryBus",clientIdPrefix = "${kafka.bus.event-topic}")
	public void bus(@Payload String payload, @Headers MessageHeaders messageHeaders, Acknowledgment acknowledgment) {
		try {
			log.info("Payload Received: {}",payload);
			BusEvent busEvent = RouteUtil.jsonToEntity(payload,BusEvent.class,objectMapper);
			eventRouter.processEvent(busEvent);
		} catch (Exception ex) {
			log.error("Exception occurred while batch processing of txns", ex);
		} finally {
			KafkaUtil.commitOffset(acknowledgment,messageHeaders);
		}
	}

	/*
	 * @param payload
	 * @param messageHeaders
	 * @param acknowledgment
	 */
	@KafkaListener(topics = "${kafka.flight.event-topic}", containerFactory = "kafkaListenerTxnContainerFactoryFlight",clientIdPrefix = "${kafka.flight.event-topic}")
	public void flight(@Payload String payload, @Headers MessageHeaders messageHeaders, Acknowledgment acknowledgment) {
		try {
			log.info("Payload Received: {}",payload);
			FlightEvent flightEvent = RouteUtil.jsonToEntity(payload,FlightEvent.class,objectMapper);
			eventRouter.processEvent(flightEvent);
		} catch (Exception ex) {
			log.error("Exception occurred while batch processing of txns", ex);
		} finally {
			KafkaUtil.commitOffset(acknowledgment,messageHeaders);
		}
	}



}