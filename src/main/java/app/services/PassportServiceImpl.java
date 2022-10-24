package app.services;

import app.entities.Passport;
import app.repositories.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassportServiceImpl implements PassportService {

    private PassportRepository passportRepository;

    @Autowired
    public PassportServiceImpl(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    @Transactional
    public Passport save(Passport passport) {
        return passportRepository.save(passport);
    }

    @Override
    public List<Passport> findAll() {
        List<Passport> passportList = new ArrayList<>();
        passportRepository.findAll().forEach(n -> passportList.add(n));
        return passportList;
    }

    @Override
    public Passport findById(Long id) {
        return passportRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Passport> findBySerialNumberPassport(String serialNumberPassport) {
        return passportRepository.findBySerialNumberPassport(serialNumberPassport);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!passportRepository.findById(id).isPresent()) {
            return;
        }
        passportRepository.deleteById(id);
    }
}
