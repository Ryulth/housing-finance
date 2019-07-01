package kakaopay.housingfinance.service;

import kakaopay.housingfinance.converter.FileConverter;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    private final FileConverter fileConverter;

    public DataService(FileConverter fileConverter) {
        this.fileConverter = fileConverter;
    }
    public void ConvertFile(){
        fileConverter.fileConvert();
    }
}
