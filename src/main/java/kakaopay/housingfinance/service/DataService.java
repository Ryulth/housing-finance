package kakaopay.housingfinance.service;

import kakaopay.housingfinance.converter.FileConverter;
import kakaopay.housingfinance.converter.ObjectConverter;
import kakaopay.housingfinance.entity.Bank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final FileConverter fileConverter;
    private final ObjectConverter objectConverter;
    public DataService(FileConverter fileConverter, ObjectConverter objectConverter) {
        this.fileConverter = fileConverter;
        this.objectConverter = objectConverter;
    }
    public void ConvertFile(){
        List<List<String>> convertData = fileConverter.fileConvert();
        List<Bank> banks = objectConverter.bankMapper(convertData.get(0));

    }
}
