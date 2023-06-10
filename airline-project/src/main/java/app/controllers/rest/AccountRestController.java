package app.controllers.rest;

import app.controllers.api.rest.AccountRestApi;
import app.dto.account.AccountDTO;
import app.entities.account.Account;
import app.entities.account.Role;
import app.services.interfaces.AccountService;
import app.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MethodNotSupportedException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountRestController implements AccountRestApi {

    private final AccountService accountService;
    private final RoleService roleService;

    @Override
    public ResponseEntity<Page> getAll(Pageable pageable) {
        log.info("getAll: get all Accounts");
        var users = accountService.getAllAccounts(pageable);
        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDTO> getById(Long id) {
        log.info("getById: get Account by id. id = {}", id);
        var account = accountService.getAccountById(id);
        return account.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(new AccountDTO(account.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDTO> getAuthenticatedAccount() {
        log.info("getAuthenticatedAccount: get currently authenticated Account");
        Account authAccount = accountService.getAccountByEmail(
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ResponseEntity.ok(new AccountDTO(authAccount));
    }

    @Override
    public ResponseEntity<AccountDTO> create(AccountDTO accountDTO)
            throws MethodNotSupportedException {
        log.info("create: create new Account with email={}", accountDTO.getEmail());
        accountService.saveAccount(accountDTO.convertToEntity());
        return ResponseEntity.ok(new AccountDTO(accountService.getAccountByEmail(accountDTO.getEmail())));
    }

    @Override
    public ResponseEntity<AccountDTO> update(Long id, AccountDTO accountDTO)
            throws MethodNotSupportedException {
        log.info("update: update Account with id = {}", id);
        accountService.updateAccount(id, accountDTO.convertToEntity());
        return new ResponseEntity<>(new AccountDTO(accountService.getAccountById(id).get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        log.info("delete: delete Account with id = {}", id);
        var user = accountService.getAccountById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> allRolesFromDb = roleService.getAllRoles();
        if (allRolesFromDb.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(allRolesFromDb);
    }
}