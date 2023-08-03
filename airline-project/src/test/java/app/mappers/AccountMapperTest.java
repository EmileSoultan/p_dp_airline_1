package app.mappers;

import app.dto.AccountDTO;
import app.entities.account.Account;
import app.entities.account.Role;
import app.services.interfaces.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Set;


class AccountMapperTest {

    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    @Mock
    private RoleService roleService;

    @Test
    public void shouldConvertAccountToAccountDTO() throws Exception {

        Account account = new Account();

        account.setId(1L);
        account.setFirstName("Ivan");
        account.setLastName("Ivanov");
        account.setBirthDate(LocalDate.of(2023, 8, 3));
        account.setPhoneNumber("7933333333");
        account.setEmail("manager2@mail.ru");
        account.setPassword("Test123@");
        account.setAnswerQuestion("Test");
        account.setSecurityQuestion("Test");
        Mockito.when(account.setRoles(Set.of(roleService.getRoleByName("ROLE_MANAGER")))).thenReturn("ROLE_MANEGER");

        AccountDTO accountDTO = accountMapper.convertToAccountDTO(account);

        Assertions.assertEquals(account.getId(), accountDTO.getId());
        Assertions.assertEquals(account.getFirstName(), accountDTO.getFirstName());
        Assertions.assertEquals(account.getLastName(), accountDTO.getLastName());
        Assertions.assertEquals(account.getBirthDate(), accountDTO.getBirthDate());
        Assertions.assertEquals(account.getPhoneNumber(), accountDTO.getPhoneNumber());
        Assertions.assertEquals(account.getEmail(), accountDTO.getEmail());
        Assertions.assertEquals(account.getPassword(), accountDTO.getPassword());
        Assertions.assertEquals(account.getAnswerQuestion(), accountDTO.getAnswerQuestion());
        Assertions.assertEquals(account.getSecurityQuestion(), accountDTO.getSecurityQuestion());
        Assertions.assertEquals(account.getRoles(), accountDTO.getRoles());

    }

    @Test
    public void shouldConvertAccountDTOToAccount() throws Exception {

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setId(1L);
        accountDTO.setFirstName("Ivan");
        accountDTO.setLastName("Ivanov");
        accountDTO.setBirthDate(LocalDate.of(2023, 8, 3));
        accountDTO.setPhoneNumber("7933333333");
        accountDTO.setEmail("manager2@mail.ru");
        accountDTO.setPassword("Test123@");
        accountDTO.setAnswerQuestion("Test");
        accountDTO.setSecurityQuestion("Test");
//        accountDTO.setRoles(Set.of(roleServiceMock.getRoleByName("ROLE_MANAGER")));

        Account account = accountMapper.convertToAccount(accountDTO);

        Assertions.assertEquals(accountDTO.getId(), account.getId());
        Assertions.assertEquals(accountDTO.getFirstName(), account.getFirstName());
        Assertions.assertEquals(accountDTO.getLastName(), account.getLastName());
        Assertions.assertEquals(accountDTO.getBirthDate(), account.getBirthDate());
        Assertions.assertEquals(accountDTO.getPhoneNumber(), account.getPhoneNumber());
        Assertions.assertEquals(accountDTO.getEmail(), account.getEmail());
        Assertions.assertEquals(accountDTO.getPassword(), account.getPassword());
        Assertions.assertEquals(accountDTO.getAnswerQuestion(), account.getAnswerQuestion());
        Assertions.assertEquals(accountDTO.getSecurityQuestion(), account.getSecurityQuestion());
        Assertions.assertEquals(accountDTO.getRoles(), account.getRoles());


    }
}
