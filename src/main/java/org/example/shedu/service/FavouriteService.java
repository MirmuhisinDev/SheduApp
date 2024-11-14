package org.example.shedu.service;

import lombok.RequiredArgsConstructor;
import org.example.shedu.repository.FavouriteRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
}
