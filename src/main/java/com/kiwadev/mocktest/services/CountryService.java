package com.kiwadev.mocktest.services;

import com.kiwadev.mocktest.models.web.CountryRequestDTO;
import com.kiwadev.mocktest.models.web.CountryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CountryService {

    CountryResponseDTO create(CountryRequestDTO dto);

    CountryResponseDTO update(String code, CountryRequestDTO dto);

    void delete(String code);

    CountryResponseDTO findById(String code);

    Page<CountryResponseDTO> findAll(Pageable pageable);
}

