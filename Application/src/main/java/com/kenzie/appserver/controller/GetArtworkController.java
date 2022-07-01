package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ArtworkResponse;
import com.kenzie.appserver.service.ArtworkService;
import com.kenzie.appserver.service.model.Artwork;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artwork")
public class GetArtworkController {
    private ArtworkService artworkService;

    GetArtworkController(ArtworkService artworkService){this.artworkService = artworkService;}

    @GetMapping
    public ResponseEntity<List<ArtworkResponse>> getAllArtwork() {
        List<Artwork> artwork = artworkService.findAllArtwork();
        // If there are no concerts, then return a 204
        if (artwork == null ||  artwork.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Otherwise, convert the List of Concert objects into a List of ConcertResponses and return it
        List<ArtworkResponse> response = new ArrayList<>();
        for (Artwork artwork1 : artwork) {
            response.add(this.createArtworkResponse(artwork1));
        }

        return ResponseEntity.ok(response);
    }

    private ArtworkResponse createArtworkResponse(Artwork artwork) {
        ArtworkResponse artworkResponse = new ArtworkResponse();
        artworkResponse.setDateCreated(artwork.getDateCreated());
        artworkResponse.setArtistName(artwork.getArtistName());
        artworkResponse.setTitle(artwork.getTitle());
        artworkResponse.setForSale(artwork.getIsForSale());
        artworkResponse.setSold(artwork.getIsSold());
        artworkResponse.setPrice(artwork.getPrice().intValue());

        return artworkResponse;
    }
}
