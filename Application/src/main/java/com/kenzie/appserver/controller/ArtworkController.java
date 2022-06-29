package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ArtworkResponse;
import com.kenzie.appserver.controller.model.ArtworkCreateRequest;
import com.kenzie.appserver.controller.model.ArtworkUpdateRequest;
import com.kenzie.appserver.service.ArtworkService;

import com.kenzie.appserver.service.model.Artwork;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artwork")
public class ArtworkController {

    private ArtworkService artworkService;

    ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }
    //WE WILL NOT HAVE A GET FOR ID ONLY, BUT WILL HAVE A GET FOR GET ALL -LAURIE
//    @GetMapping("/id}")
//    public ResponseEntity<ArtworkResponse> get(@PathVariable("id") String id) {
//
//        Artwork artwork = artworkService.findById(id);
//        if (artwork == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        ArtworkResponse artworkResponse = new ArtworkResponse();
//        artworkResponse.setId(artwork.getId());
//        artworkResponse.setName(example.getName());
//        return ResponseEntity.ok(exampleResponse);
//    }

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
        artworkResponse.setArtistName(artwork.getArtistName());
        artworkResponse.setTitle(artwork.getTitle());
        artworkResponse.setDateCreated(artwork.getDateCreated());
        artworkResponse.setSold(artwork.getIsSold());
        artworkResponse.setForSale(artwork.getIsForSale());
        return artworkResponse;
    }
}
