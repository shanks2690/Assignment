package publisher.service;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.io.ResourceLoader;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import publisher.mq.ItemPublisher;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
@Singleton
public class FileReaderPublisher {
    private final ItemPublisher publisher;
    @Value("${file.path}")
    private String filePath;
    @Inject
    private ResourceLoader resourceLoader;
    public FileReaderPublisher(ItemPublisher publisher) {
        this.publisher = publisher;
    }
    @EventListener
    public void onStartup(ServerStartupEvent event) throws Exception {
        BufferedReader br;
        if (filePath.startsWith("classpath:")) {
            String res = filePath.substring("classpath:".length());
            br = new BufferedReader(new InputStreamReader(resourceLoader.getResource(res).orElseThrow().openStream()));
        } else {
            br = Files.newBufferedReader(Paths.get(filePath));
        }
        try (br) {
            String line;
            while ((line = br.readLine()) != null) {
                publisher.send(line);
            }
        }
    }
}