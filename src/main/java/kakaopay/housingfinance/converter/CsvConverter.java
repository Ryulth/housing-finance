package kakaopay.housingfinance.converter;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvConverter implements FileConverter {
    @Value("${kakaopay.housing.finance.file.name}")
    private String filePath;

    @Override
    public void fileConvert() {
        System.out.println(readCsvFile());
    }

    public List<List<String>> readCsvFile() {

        ClassPathResource resource = new ClassPathResource(filePath);
        List<List<String>> list = new ArrayList<>();
        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

        try {
            File csvFile = new File(Paths.get(resource.getURI()).toString());
            MappingIterator<String[]> it = mapper.readerFor(String[].class).readValues(csvFile);
            while (it.hasNext()) {
                String[] row = it.next();
                list.add(Arrays.asList(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
