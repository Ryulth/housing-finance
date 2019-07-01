package kakaopay.housingfinance.service;

import kakaopay.housingfinance.converter.FileConverter;
import kakaopay.housingfinance.converter.ObjectConverter;
import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.SupplyStatus;
import kakaopay.housingfinance.repository.BankRepository;
import kakaopay.housingfinance.repository.SupplyStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataService implements CommandLineRunner {
    private final FileConverter fileConverter;
    private final ObjectConverter objectConverter;
    private final BankRepository bankRepository;
    private final SupplyStatusRepository supplyStatusRepository;

    public DataService(FileConverter fileConverter, ObjectConverter objectConverter, BankRepository bankRepository, SupplyStatusRepository supplyStatusRepository) {
        this.fileConverter = fileConverter;
        this.objectConverter = objectConverter;
        this.bankRepository = bankRepository;
        this.supplyStatusRepository = supplyStatusRepository;
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
        List<SupplyStatus> supplyStatuses = objectConverter.supplyStatusListMapper(rowData,banks);
        supplyStatusRepository.saveAll(supplyStatuses);
    }


}
