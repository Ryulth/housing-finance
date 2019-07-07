package kakaopay.housingfinance.service;

import kakaopay.housingfinance.converter.CsvConverter;
import kakaopay.housingfinance.converter.FileConverter;
import kakaopay.housingfinance.converter.ObjectConverter;
import kakaopay.housingfinance.repository.BankFinanceRepository;
import kakaopay.housingfinance.repository.BankRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceTest {

    @InjectMocks
    DataService dataService;

    @Mock
    BankRepository bankRepository;
    @Mock
    BankFinanceRepository bankFinanceRepository;
    @Mock
    FileConverter fileConverter ;
    @InjectMocks
    CsvConverter csvConverter;

    @Spy
    ObjectConverter objectConverter;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(csvConverter, "filePath", "housing-finance-data.csv");
        when(fileConverter.convertFile())
                .thenReturn(csvConverter.convertFile());
    }

    @Test
    public void testSaveFileToDatabase(){
        dataService.saveFileToDatabase();
        verify(fileConverter,atLeastOnce()).convertFile();
        verify(objectConverter,atLeastOnce()).bankListMapper(anyList());
        verify(bankRepository,atLeastOnce()).saveAll(anyList());
        verify(objectConverter,atLeastOnce()).bankFinanceListMapper(anyList(),anyList());
        verify(bankFinanceRepository,atLeastOnce()).saveAll(anyList());
    }

    @Test
    public void testRun() throws Exception {
        dataService.run("");
        verify(fileConverter,atLeastOnce()).convertFile();
        verify(objectConverter,atLeastOnce()).bankListMapper(anyList());
        verify(bankRepository,atLeastOnce()).saveAll(anyList());
        verify(objectConverter,atLeastOnce()).bankFinanceListMapper(anyList(),anyList());
        verify(bankFinanceRepository,atLeastOnce()).saveAll(anyList());
    }
}