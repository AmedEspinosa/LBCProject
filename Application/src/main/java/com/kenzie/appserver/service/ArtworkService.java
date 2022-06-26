package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ArtworkRepository;
import com.kenzie.appserver.repositories.model.ArtworkRecord;
import com.kenzie.appserver.service.model.Artwork;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtworkService {
    private ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public Artwork findById(String id) {
        Artwork artworkFromBackend = artworkRepository
                .findById(id)
                .map(artwork -> new Artwork(artwork.getId(), artwork.getDatePosted(), artwork.getArtistName(),
                        artwork.getTitle(), artwork.getDateCreated(), artwork.getHeight(), artwork.getWidth(),
                        artwork.getIsSold(), artwork.getIsForSale(), artwork.getPrice()))
                //.map(example -> new Example(example.getId(), example.getName())) //ORIGINAL LINE
                .orElse(null);

        return artworkFromBackend;
    }

    public Artwork addNewExample(Artwork artwork) {
        ArtworkRecord artworkRecord = new ArtworkRecord();
        artworkRecord.setId(artwork.getId());
        artworkRecord.setDatePosted(artwork.getDatePosted());
        artworkRecord.setArtistName(artwork.getArtistName());
        artworkRecord.setTitle(artwork.getTitle());
        artworkRecord.setDateCreated(artwork.getDateCreated());
        artworkRecord.setHeight(artwork.getHeight());
        artworkRecord.setWidth(artwork.getWidth());
        artworkRecord.setSold(artwork.getIsSold()); //?
        artworkRecord.setForSale(artwork.getIsForSale()); //?
        artworkRecord.setPrice(artwork.getPrice());

        artworkRepository.save(artworkRecord);
        return artwork;
    }

    public List<Artwork> findAllArtwork() {
        List<Artwork> artworks = new ArrayList<>();

        Iterable<ArtworkRecord>artworkRecordIterable = artworkRepository.findAll();
        for (ArtworkRecord record : artworkRecordIterable) {
            artworks.add(new Artwork(record.getId(),record.getDatePosted(),record.getArtistName(),record.getTitle(),
                    record.getDateCreated(),record.getHeight(),record.getWidth(),record.getIsSold(),
                    record.getIsForSale(),record.getPrice()));
        }
        return artworks;
    }
}
