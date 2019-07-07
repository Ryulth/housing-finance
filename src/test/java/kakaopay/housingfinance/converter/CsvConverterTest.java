package kakaopay.housingfinance.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CsvConverterTest {

    @InjectMocks
    CsvConverter csvConverter;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(csvConverter, "filePath", "housing-finance-data.csv");
    }

    @Test
    public void testConvertFile(){
        List<List<String>> expectedList = csvConverter.convertFile();
        assertThat(expectedList,is(notNullValue()));

        List<String> firstRow = expectedList.get(0);
        assertThat(firstRow.get(0),is("연도"));
        assertThat(firstRow.get(1),is("월"));
    }

}