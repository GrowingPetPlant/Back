package Happy20.GrowingPetPlant.Arduino.Service;

import Happy20.GrowingPetPlant.Arduino.DTO.PostWateringReq;
import Happy20.GrowingPetPlant.Status.Domain.Status;
import Happy20.GrowingPetPlant.Status.Service.Port.StatusRepository;
import Happy20.GrowingPetPlant.UserPlant.Domain.UserPlant;
import Happy20.GrowingPetPlant.UserPlant.Service.Port.UserPlantRepository;
import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class ArduinoService {

    // MQTT 브로커 주소
    private static final String BROKER = "tcp://broker.mqtt-dashboard.com:1883";
    // MQTT 클라이언트 ID
    private static final String CLIENT_ID = "ksj";
    // 물 주기 TOPIC
    private static final String TOPIC_WATERING = "TOPIC_WATERING";
    // 물 주기 TOPIC
    private static final String TOPIC_LIGHT = "TOPIC_LIGHT";
    // 물 주기 TOPIC
    private static final String TOPIC_FAN = "TOPIC_FAN";

    // 물 주기 메시지
    private static final String WATERING_ON = "1";
    // 조명 켜기 메시지
    private static final String LIGHT_ON = "1";
    // 조명 끄기 메시지
    private static final String LIGHT_OFF = "0";
    // 팬 켜기 메시지
    private static final String FAN_ON = "1";
    // 팬 끄기 메시지
    private static final String FAN_OFF = "0";

    private final UserPlantRepository userPlantRepository;
    private final StatusRepository statusRepository;

    // 식물 최근 물 준 날짜 표시
    @Transactional
    public LocalDate recentPlantWatering(Long userPlantNumber) {

        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);

        return userPlant.getWateringDate();
    }

    @Transactional
    public boolean wateringPlant(PostWateringReq postWateringReq) {

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

            UserPlant userPlant = userPlantRepository.findByUserPlantNumber(postWateringReq.getUserPlantNumber());
            Status status = statusRepository.findRecentStatusByUserPlant(userPlant);
            userPlant.setWateringDate(postWateringReq.getWateringDate());
            status.setWatering(true);
            userPlantRepository.save(userPlant);
            statusRepository.save(status);

            return (true);
        }
        catch (MqttException e) {
            log.error("에러가 발생했습니다.", e);
            return (false);
        }
    }

    @Transactional
    public boolean lightingPlant(Long userPlantNumber) {

        try(MqttClient client = new MqttClient(BROKER, CLIENT_ID)) {     // MQTT 클라이언트 생성, try-with-resource : 자동 해제 위해

            // MQTT 연결 옵션 설정
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // MQTT 브로커에 연결
            client.connect(connOpts);

            UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
            Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

            if(!recentStatus.getLight()){

                // 메시지 생성 및 발행
                MqttMessage mqttMessage = new MqttMessage(LIGHT_ON.getBytes());
                mqttMessage.setQos(0); // 신뢰성 전달

                // 특정 주제에 메시지 발행
                client.publish(TOPIC_LIGHT, mqttMessage);

                // 조명 켜기
                recentStatus.setLight(true);
            }
            else {
                // 메시지 생성 및 발행
                MqttMessage mqttMessage = new MqttMessage(LIGHT_OFF.getBytes());
                mqttMessage.setQos(0); // 신뢰성 전달

                // 특정 주제에 메시지 발행
                client.publish(TOPIC_LIGHT, mqttMessage);

                // 조명 끄기
                recentStatus.setLight(false);
            }

            // 연결 해제
            client.disconnect();

            return (true);
        }
        catch (MqttException e) {
            log.error("에러가 발생했습니다.", e);
            return (false);
        }
    }

    @Transactional
    public boolean fanningPlant(Long userPlantNumber) {

        try(MqttClient client = new MqttClient(BROKER, CLIENT_ID)) {     // MQTT 클라이언트 생성, try-with-resource : 자동 해제 위해

            // MQTT 연결 옵션 설정
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            // MQTT 브로커에 연결
            client.connect(connOpts);

            UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
            Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

            if(!recentStatus.getFan()){

                // 메시지 생성 및 발행
                MqttMessage mqttMessage = new MqttMessage(FAN_ON.getBytes());
                mqttMessage.setQos(0); // 신뢰성 전달

                // 특정 주제에 메시지 발행
                client.publish(TOPIC_FAN, mqttMessage);

                // 팬 켜기
                recentStatus.setFan(true);
            }
            else {
                // 메시지 생성 및 발행
                MqttMessage mqttMessage = new MqttMessage(FAN_OFF.getBytes());
                mqttMessage.setQos(0); // 신뢰성 전달

                // 특정 주제에 메시지 발행
                client.publish(TOPIC_FAN, mqttMessage);

                // 팬 끄기
                recentStatus.setFan(false);
            }

            // 연결 해제
            client.disconnect();

            return (true);
        }
        catch (MqttException e) {
            log.error("에러가 발생했습니다.", e);
            return (false);
        }
    }

    @Transactional
    public boolean getLightingStatus(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

        if (recentStatus.getLight())
            return (true);
        else
            return (false);
    }

    @Transactional
    public boolean getFanningStatus(Long userPlantNumber) {
        UserPlant userPlant = userPlantRepository.findByUserPlantNumber(userPlantNumber);
        Status recentStatus = statusRepository.findRecentStatusByUserPlant(userPlant);

        if (recentStatus.getFan())
            return (true);
        else
            return (false);
    }
}
