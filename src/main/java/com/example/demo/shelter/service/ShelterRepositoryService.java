package com.example.demo.shelter.service;

import com.example.demo.shelter.entity.Shelter;
import com.example.demo.shelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterRepositoryService {

    private final ShelterRepository shelterRepository;

    // self invocation test
    public void bar(List<Shelter> shelterList) {
        log.info("bar CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        foo(shelterList);
    }

    // self invocation test
    @Transactional
    public void foo(List<Shelter> shelterList) {
        log.info("foo CurrentTransactionName: "+ TransactionSynchronizationManager.getCurrentTransactionName());
        shelterList.forEach(shelter -> {
           shelterRepository.save(shelter);
           throw new RuntimeException("error");
        });
    }


    // read only test
    @Transactional(readOnly = true)
    public void startReadOnlyMethod(Long id) {
        shelterRepository.findById(id).ifPresent(shelter ->
                shelter.changeShelterAddress("서울 특별시 광진구"));
    }


    @Transactional
    public List<Shelter> saveAll(List<Shelter> shelterList) {
        if(CollectionUtils.isEmpty(shelterList)) return Collections.emptyList();
        return shelterRepository.saveAll(shelterList);
    }

    @Transactional
    public void updateAddress(Long id, String address) {
        Shelter entity = shelterRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)) {
            log.error("[ShelterRepositoryService updateAddress] not found id : {}", id);
            return;
        }
        entity.changeShelterAddress(address);
    }

    // for test
    public void updateAddressWithoutTransaction(Long id, String address) {
        Shelter entity = shelterRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)) {
            log.error("[ShelterRepositoryService updateAddress] not found id : {}", id);
            return;
        }
        entity.changeShelterAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Shelter> findAll() {
        return shelterRepository.findAll();
    }


}
