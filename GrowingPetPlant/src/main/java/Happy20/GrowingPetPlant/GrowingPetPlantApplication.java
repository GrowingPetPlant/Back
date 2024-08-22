package Happy20.GrowingPetPlant;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Happy20.GrowingPetPlant.Subscribe.Subscriber;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@SpringBootApplication
@Component
@EnableScheduling
public class GrowingPetPlantApplication {

	private static String tempPayload;
	private static String humiPayload;
	private static String soilPayload;

	public static void main(String[] args) {
		SpringApplication.run(GrowingPetPlantApplication.class, args);

		// 원하는 타임존으로 지정
		TimeZone seoulTimeZone = TimeZone.getTimeZone("Asia/Seoul");

		// JVM의 기본 타임존을 변경
		TimeZone.setDefault(seoulTimeZone);

		// 변경된 타임존 확인
		TimeZone defaultTimeZone = TimeZone.getDefault();
		System.out.println("Current TimeZone ID: " + defaultTimeZone.getID());

		// Subscriber 인스턴스를 가져옴
		Subscriber subscriber = Subscriber.getInstance();

		// 초기화: 브로커 주소와 클라이언트 ID 설정
		subscriber.initialize("tcp://broker.mqtt-dashboard.com:1883", "ksj01128");

		// 콜백 설정
		subscriber.setCallback(new MqttCallback() {
			@Override
			public void connectionLost(Throwable throwable) {
				// 연결이 끊어졌을 때 처리할 작업
			}

			// 토픽 구조 예시 : userPlant/{userPlantId}/temperature
			@Override
			public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
				// 메시지 수신 시 처리할 작업
				String payload = new String(mqttMessage.getPayload());
				System.out.println("Message received on topic " + topic + ": " + payload);

				// 토픽에서 userId와 plantId 추출
				String[] topicLevels = topic.split("/");
				String userPlantId = topicLevels[1];
				String dataType = topicLevels[2];  // temperature, humidity, moisture 중 하나

				// 데이터베이스에 저장
				if (dataType.equals("temperature")) {
					tempPayload = payload;
				} else if (dataType.equals("humidity")) {
					humiPayload = payload;
				} else if (dataType.equals("moisture")) {
					soilPayload = payload;
				}

				if (tempPayload != null && humiPayload != null && soilPayload != null) {
					subscriber.insertToDatabase(Long.parseLong(userPlantId), tempPayload, humiPayload, soilPayload);
					tempPayload = null;
					humiPayload = null;
					soilPayload = null;
				}
			}

			@Override
			public void deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken iMqttDeliveryToken) {
				// 메시지 전송 완료 후 처리할 작업
			}
		});

		// 연결
		subscriber.connect();

//		// 여러 센서 구독
//		subscriber.subscribe("TOPIC_TEMP", 0);
//		subscriber.subscribe("TOPIC_HUMI", 0);
//		subscriber.subscribe("TOPIC_SOIL", 0);

		// 모든 유저-식물에 대한 토픽 구독
		subscriber.subscribe("userPlant/+/temperature", 0);
		subscriber.subscribe("userPlant/+/humidity", 0);
		subscriber.subscribe("userPlant/+/moisture", 0);
	}
}