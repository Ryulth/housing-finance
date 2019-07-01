package kakaopay.housingfinance.repository;

import kakaopay.housingfinance.entity.SupplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyStatusRepository extends JpaRepository<SupplyStatus,Long> {
}
