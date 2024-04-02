package Happy20.GrowingPetPlant.Arduino.Service;

import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ArduinoService {

    // MQTT 브로커 주소
    private static final String BROKER = "tcp://broker.mqtt-dashboard.com:1883";
    // MQTT 클라이언트 ID
    private static final String CLIENT_ID = "ksj";
    // 물 주기 TOPIC
    private static final String TOPIC_WATERING = "watering_start_1128";
    // 물 주기 TOPIC
    private static final String TOPIC_LIGHT_ON = "";
    // 물 주기 TOPIC
    private static final String TOPIC_LIGHT_OFF = "";
    // 물 주기 TOPIC
    private static final String TOPIC_FAN_ON = "";
    // 물 주기 TOPIC
    private static final String TOPIC_FAN_OFF = "";

    // 물 주기 메시지
    private static final String WATERING_ON = "0";
    // 조명 켜기 메시지
    private static final String LIGHT_ON = "1";
    // 조명 끄기 메시지
    private static final String LIGHT_OFF = "2";
    // 팬 켜기 메시지
    private static final String FAN_ON = "3";
    // 팬 끄기 메시지
    private static final String FAN_OFF = "4";

    @Transactional
    public boolean wateringPlant() {

        try(MqttClient client = new MqttClient(BROKER, CLIENT_ID)) {     // MQTT 클라이언트 생성, try-with-resource : 자동 해제 위해

            // MQTT 연결 옵션 설정
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // MQTT 브로커에 연결
            client.connect(connOpts);

            // 메시지 생성 및 발행
            MqttMessage mqttMessage = new MqttMessage(WATERING_ON.getBytes());
            mqttMessage.setQos(0); // 신뢰성 전달

            // 특정 주제에 메시지 발행
            client.publish(TOPIC_WATERING, mqttMessage);

            // 연결 해제
            client.disconnect();

            return (true);
        }
        catch (MqttException e) {
            log.error("에러가 발생했습니다.", e);
            return (false);
        }
    }
}
