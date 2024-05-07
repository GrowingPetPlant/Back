package Happy20.GrowingPetPlant.Status;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByPlantNumber(Long plantNumber); // 아이디로 사용자 조회
}