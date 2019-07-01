package kakaopay.housingfinance.repository;

import kakaopay.housingfinance.entity.BankFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankFinanceRepository extends JpaRepository<BankFinance,Long> {
}
