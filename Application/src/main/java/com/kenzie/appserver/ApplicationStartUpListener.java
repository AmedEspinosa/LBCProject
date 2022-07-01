package com.kenzie.appserver;


import com.kenzie.appserver.service.ArtworkService;
import com.kenzie.appserver.service.model.Artwork;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@Component
public class ApplicationStartUpListener {

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

            ArtworkService artworkService = event.getApplicationContext()
                    .getBean(ArtworkService.class);
            ConcurrentLinkedQueue queue = event.getApplicationContext().getBean(ConcurrentLinkedQueue.class);
            List<Artwork> artworkList = artworkService.findAllArtwork();
            queue.addAll(artworkList);


    }
}
