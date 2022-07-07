package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ArtworkResponse;
import com.kenzie.appserver.controller.model.ArtworkCreateRequest;
import com.kenzie.appserver.controller.model.ArtworkUpdateRequest;
import com.kenzie.appserver.service.ArtworkService;

import com.kenzie.appserver.service.model.Artwork;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/artwork")
public class ArtworkController {

    private ArtworkService artworkService;

    ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponse> getArtwork(@PathVariable("id") String id) {

        Artwork artwork = artworkService.findById(id);
        if (artwork == null) {
            return ResponseEntity.notFound().build();
        }

        ArtworkResponse artworkResponse = createArtworkResponse(artwork);
        return ResponseEntity.ok(artworkResponse);
    }

    @PostMapping
    public ResponseEntity<ArtworkResponse> addNewArtwork(@RequestBody ArtworkCreateRequest artworkCreateRequest) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Artwork artwork = new Artwork(randomUUID().toString(),
                now.format(formatter),
                artworkCreateRequest.getArtistName(),
                artworkCreateRequest.getTitle(),
                artworkCreateRequest.getDateCreated(),
                artworkCreateRequest.getHeight(),
                artworkCreateRequest.getWidth(),
                false,
                artworkCreateRequest.getIsForSale(),
                artworkCreateRequest.getPrice());

        artworkService.addNewArtwork(artwork);
        ArtworkResponse artworkResponse = createArtworkResponse(artwork);

        return ResponseEntity.created(URI.create("/artwork/" + artworkResponse.getId())).body(artworkResponse);

    }

    @PutMapping
    public ResponseEntity<ArtworkResponse> updateArtwork(@RequestBody ArtworkUpdateRequest artworkUpdateRequest) {
        Artwork artwork = new Artwork(artworkUpdateRequest.getId(),
                artworkUpdateRequest.getDatePosted(),
                artworkUpdateRequest.getArtistName(),
                artworkUpdateRequest.getTitle(),
                artworkUpdateRequest.getDateCreated(),
                artworkUpdateRequest.getHeight(),
                artworkUpdateRequest.getWidth(),
                artworkUpdateRequest.getIsSold(),
                artworkUpdateRequest.getIsForSale(),
                artworkUpdateRequest.getPrice());
        artworkService.updateArtwork(artwork);
        ArtworkResponse artworkResponse = createArtworkResponse(artwork);
        return ResponseEntity.ok(artworkResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArtworkById(@PathVariable("id") String id) {
        artworkService.deleteArtwork(id);
        return ResponseEntity.noContent().build();
    }

    private ArtworkResponse createArtworkResponse(Artwork artwork) {
        ArtworkResponse artworkResponse = new ArtworkResponse();
        artworkResponse.setId(artwork.getId());
        artworkResponse.setArtistName(artwork.getArtistName());
        artworkResponse.setTitle(artwork.getTitle());
        artworkResponse.setDateCreated(artwork.getDateCreated());
        artworkResponse.setDatePosted(artwork.getDatePosted());
        artworkResponse.setHeight(artwork.getHeight());
        artworkResponse.setWidth(artwork.getWidth());
        artworkResponse.setSold(artwork.getIsSold());
        artworkResponse.setForSale(artwork.getIsForSale());
        artworkResponse.setPrice(artwork.getPrice());
        return artworkResponse;
    }
}
