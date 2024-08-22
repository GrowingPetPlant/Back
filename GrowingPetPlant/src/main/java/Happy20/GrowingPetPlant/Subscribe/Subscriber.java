package Happy20.GrowingPetPlant.Subscribe;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class Subscriber {
    private static final Logger log = LoggerFactory.getLogger(Subscriber.class);
    private static Subscriber instance;
    private MqttClient client;
    private MqttConnectOptions connOpts;

//    @Value("${spring.datasource.url}")
    private String DB_URL = "jdbc:mysql://34.64.250.250:3306/gpp";

//    @Value("${spring.datasource.username}")
    private String DB_USER ="root";

//    @Value("${spring.datasource.password}")
    private String DB_PASSWORD = "gpppassword";

    // Private 생성자로 외부에서 인스턴스 생성을 막음
    private Subscriber() {}

    // 인스턴스 반환 메서드
    public static synchronized Subscriber getInstance() {
        if (instance == null) {
            instance = new Subscriber();
        }
        return instance;
    }

    // 초기화 메서드
    public void initialize(String broker, String clientId) {
        try {
            client = new MqttClient(broker, clientId, new MemoryPersistence());
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true); // 자동 재연결 활성화
            connOpts.setConnectionTimeout(10); // 연결 시간 초과 설정 (옵션)
            connOpts.setKeepAliveInterval(60); // Keep Alive 간격 설정 (옵션)
        } catch (MqttException e) {
            log.error("Failed to initialize subscriber", e);
        }
    }

    // 구독 메서드
    public void subscribe(String topic, int qos) {
        try {
            if (!client.isConnected()) {
                log.warn("Client is not connected. Subscribing will be delayed.");
                return;
            }
            log.info("Subscribing to topic: " + topic);
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            log.error("Failed to subscribe to topic", e);
        }
    }

    // 연결 메서드
    public void connect() {
        try {
            log.info("Connecting to broker: " + client.getServerURI());
            client.connect(connOpts);
            log.info("Connected");
        } catch (MqttException e) {
            log.error("Failed to connect to broker", e);
        }
    }

    // 콜백 설정 메서드
    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }

    // 데이터베이스에 데이터 삽입 메서드
    public void insertToDatabase(Long userPlantId, String tempPayload, String humiPayload, String soilPayload) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            // 데이터를 삽입할 테이블 이름
            String query = "UPDATE status SET temperature = ?, humidity = ?, moisture = ? WHERE user_plant_number = ? ORDER BY status_number DESC LIMIT 1";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {

                // 각 토픽의 값을 쿼리에 설정
                pstmt.setDouble(1, Double.parseDouble(tempPayload));
                pstmt.setDouble(2, Double.parseDouble(humiPayload));
                pstmt.setDouble(3, Double.parseDouble(soilPayload));
                pstmt.setLong(4, userPlantId);
                pstmt.executeUpdate();

                System.out.println("테스트 : " + tempPayload + "\n");
                System.out.println("테스트 : " + humiPayload + "\n");
                System.out.println("테스트 : " + soilPayload + "\n");
                System.out.println("테스트 : " + userPlantId + "\n");
            }
        } catch (SQLException e) {
            log.error("Failed to insert data into database", e);
        }
    }
}

