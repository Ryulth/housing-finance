package kakaopay.housingfinance.repository;

import kakaopay.housingfinance.entity.BankFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankFinanceRepository extends JpaRepository<BankFinance,Long> {
    List<BankFinance> findAllByBankId(Long bankId);
}
