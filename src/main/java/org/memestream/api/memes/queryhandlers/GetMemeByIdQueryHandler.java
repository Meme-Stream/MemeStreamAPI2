package org.memestream.api.memes.queryhandlers;

import org.memestream.api.Query;
import org.memestream.api.memes.MemeMetadataRepository;
import org.memestream.api.memes.MemeStorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class GetMemeByIdQueryHandler implements Query<String, ResponseEntity<Resource>> {

    private final MemeStorageProperties memeStorageProperties;
    private final Path basePath;

    private final MemeMetadataRepository memeMetadataRepository;

    public GetMemeByIdQueryHandler(MemeStorageProperties memeStorageProperties,
                                   MemeMetadataRepository memeMetadataRepository) {
        this.memeStorageProperties = memeStorageProperties;
        this.basePath = Paths.get(memeStorageProperties.getBasePath());
        this.memeMetadataRepository = memeMetadataRepository;
    }

    @Override
    public ResponseEntity<Resource> execute(String stringUUID) throws MalformedURLException {
        var uuid = UUID.fromString(stringUUID);
        var memeMetadata = memeMetadataRepository.findById(uuid).orElseThrow(() -> new RuntimeException("meta data not found"));
        var creationDate = LocalDate.ofInstant(memeMetadata.getCreationTime(), ZoneId.systemDefault());

        var filePath = basePath.resolve(
                creationDate.getYear() + File.separator +
                        String.format("%02d", creationDate.getMonthValue()) + File.separator +
                        String.format("%02d", creationDate.getDayOfMonth()) + File.separator +
                        memeMetadata.getStoredFilename()
        );

        if (!Files.exists(filePath))
            throw new RuntimeException("Meme doesn't exist");

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(memeMetadata.getMimeType()))
                .contentLength(memeMetadata.getSize())
                .body(new UrlResource(filePath.normalize().toUri()));
    }
}
