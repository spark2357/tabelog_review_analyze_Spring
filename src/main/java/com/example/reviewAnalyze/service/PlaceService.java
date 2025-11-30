package com.example.reviewAnalyze.service;

import com.example.reviewAnalyze.entity.Place;
import com.example.reviewAnalyze.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }
}
