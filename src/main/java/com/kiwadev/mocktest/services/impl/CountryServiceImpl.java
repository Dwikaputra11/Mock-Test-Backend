package com.kiwadev.mocktest.services.impl;

import com.kiwadev.mocktest.models.domain.Actor;
import com.kiwadev.mocktest.models.domain.Country;
import com.kiwadev.mocktest.models.web.CountryRequestDTO;
import com.kiwadev.mocktest.models.web.CountryResponseDTO;
import com.kiwadev.mocktest.repository.CountryRepository;
import com.kiwadev.mocktest.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    public CountryRepository countryRepository;

    @Override
    public CountryResponseDTO create(CountryRequestDTO dto) {
        if(dto.getName().isEmpty()){
            throw new RuntimeException("Data actor is not valid");
        }
        Country country = new Country(dto.getCountryCode(), dto.getName());
        return countryRepository.save(country).toResponse();
    }

    @Override
    public CountryResponseDTO update(String code, CountryRequestDTO dto) {
        if(dto.getName().isEmpty()){
            throw new RuntimeException("Data user is not valid");
        }

        return  countryRepository.findById(code)
                .map(existing -> {
                    existing.setName(dto.getName());
                    return  countryRepository.save(existing).toResponse();
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(String code) {
        if(!countryRepository.existsById(code))
            throw new RuntimeException("Country code "+code+" not found");
        countryRepository.deleteById(code);
    }

    @Override
    public CountryResponseDTO findById(String code) {
        var data = countryRepository.findById(code);
        if(data.isEmpty()) throw new RuntimeException("Country code : " +code+" not found.");
        return data.get().toResponse();
    }

    @Override
    public Page<CountryResponseDTO> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable).map(Country::toResponse);
    }
}
