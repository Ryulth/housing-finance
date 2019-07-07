package kakaopay.housingfinance.service;

import kakaopay.housingfinance.dto.BankDto;
import kakaopay.housingfinance.entity.Bank;
import kakaopay.housingfinance.repository.BankRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceTest {
    @InjectMocks
    BankService bankService;

    @Mock
    BankRepository bankRepository;

    @Before
    public void setUp() {
        mockingRepository();
    }

    @Test
    public void testGetAllBanks(){
        List<BankDto> bankDtoList = new ArrayList<>();
        bankDtoList.add(BankDto.builder().name("카카오페이").build());
        bankDtoList.add(BankDto.builder().name("신한은행").build());

        Map<String,Object> expectedMap = new HashMap<>();
        expectedMap.put("bank",bankDtoList);

        Map<String,Object> resultMap = bankService.getAllBanks();

        assertThat(resultMap,is(notNullValue()));
        assertThat(resultMap,is(expectedMap));
    }

    private void mockingRepository() {
        List<Bank> banks = new ArrayList<>();
        banks.add(Bank.builder().id(1L).name("카카오페이").build());
        banks.add(Bank.builder().id(2L).name("신한은행").build());
        when(bankRepository.findAll()).thenReturn(banks);

    }

}