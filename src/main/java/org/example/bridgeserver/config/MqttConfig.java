package org.example.bridgeserver.config;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT 관련 설정 클래스입니다.
 */
@Configuration
@Slf4j
public class MqttConfig {

    @Bean
    @ConditionalOnProperty(name = "bridge.mode", havingValue = "mqtt")
    public MqttClient mqttClient(
            @Value("${mqtt.server.uri}") String uri,
            @Value("${mqtt.client.id}") String clientId,
            @Value("${mqtt.subscribe.topic}") String[] topic,
            MqttCallback mqttCallback) throws MqttException {

            MqttClient mqttClient = new MqttClient(uri, clientId);
            mqttClient.setCallback(mqttCallback);
            mqttClient.connect();
            log.info("MqttClient has connected to uri '{}'", uri);
            mqttClient.subscribe(topic);
            log.info("MqttClient has subscribed to topic '{}'", Arrays.toString(topic));

            return mqttClient;
    }
}