package kakaopay.housingfinance.service;

import kakaopay.housingfinance.converter.FileConverter;
import kakaopay.housingfinance.converter.ObjectConverter;
import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.BankFinance;
import kakaopay.housingfinance.repository.BankRepository;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataService implements CommandLineRunner {
    private final FileConverter fileConverter;
    private final ObjectConverter objectConverter;
    private final BankRepository bankRepository;
    private final BankFinanceRepository bankFinanceRepository;

    public DataService(FileConverter fileConverter, ObjectConverter objectConverter, BankRepository bankRepository, BankFinanceRepository bankFinanceRepository) {
        this.fileConverter = fileConverter;
        this.objectConverter = objectConverter;
        this.bankRepository = bankRepository;
        this.bankFinanceRepository = bankFinanceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ConvertFile();
    }

    @Transactional
    public void ConvertFile(){
        List<List<String>> rowData = fileConverter.fileConvert();
        List<String> columnNames = rowData.remove(0);
        List<Bank> banks = objectConverter.bankListMapper(columnNames);
        bankRepository.saveAll(banks);
        List<BankFinance> bankFinances = objectConverter.bankFinanceListMapper(rowData,banks);
        bankFinanceRepository.saveAll(bankFinances);
    }


}
