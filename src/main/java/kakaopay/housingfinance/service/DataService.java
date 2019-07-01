package kakaopay.housingfinance.service;

import kakaopay.housingfinance.converter.FileConverter;
import kakaopay.housingfinance.converter.ObjectConverter;
import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.entity.SupplyStatus;
import kakaopay.housingfinance.repository.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataService {
    private final FileConverter fileConverter;
    private final ObjectConverter objectConverter;
    private final BankRepository bankRepository;
    public DataService(FileConverter fileConverter, ObjectConverter objectConverter, BankRepository bankRepository) {
        this.fileConverter = fileConverter;
        this.objectConverter = objectConverter;
        this.bankRepository = bankRepository;
    }

    @Transactional
    public void ConvertFile(){
        List<List<String>> rowData = fileConverter.fileConvert();
        List<String> columnNames = rowData.remove(0);
        List<Bank> banks = objectConverter.bankListMapper(columnNames);
        bankRepository.saveAll(banks);
        List<SupplyStatus> supplyStatuses = objectConverter.supplyStatusListMapper(rowData,banks);
        

    }
}
