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

    @Transactional
    public List<Shelter> saveAll(List<Shelter> shelterList) {
        if (CollectionUtils.isEmpty(shelterList))
            return Collections.emptyList();
        return shelterRepository.saveAll(shelterList);
    }

    @Transactional(readOnly = true)
    public List<Shelter> findAll() {
        return shelterRepository.findAll();
    }

}
