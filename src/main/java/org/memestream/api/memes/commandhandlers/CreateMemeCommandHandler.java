package org.memestream.api.memes.commandhandlers;

import org.memestream.api.Command;
import org.memestream.api.chefs.ChefRepository;
import org.memestream.api.memes.MemeMetadataRepository;
import org.memestream.api.memes.MemeStorageProperties;
import org.memestream.api.memes.model.MemeMetadata;
import org.memestream.api.memes.model.UploadMemeDTO;
import org.memestream.api.model.SimpleMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateMemeCommandHandler implements Command<UploadMemeDTO, ResponseEntity<SimpleMessage>> {

    private final MemeStorageProperties memeStorageProperties;
    private final Path basePath;

    private final ChefRepository chefRepository;
    private final MemeMetadataRepository memeMetadataRepository;

    public CreateMemeCommandHandler(MemeStorageProperties memeStorageProperties,
                                    ChefRepository chefRepository,
                                    MemeMetadataRepository memeMetadataRepository) {
        this.memeStorageProperties = memeStorageProperties;
        this.basePath = Paths.get(memeStorageProperties.getBasePath());
        this.chefRepository = chefRepository;
        this.memeMetadataRepository = memeMetadataRepository;
    }

    @Override
    public ResponseEntity<SimpleMessage> execute(UploadMemeDTO uploadMemeDTO) throws IOException {
        var file = uploadMemeDTO.getFile();
        validateFile(file);

        var today = LocalDate.now();
        var dateDirectory = basePath.resolve(
                today.getYear() + File.separator +
                        String.format("%02d", today.getMonthValue()) + File.separator +
                        String.format("%02d", today.getDayOfMonth())
        );

        Files.createDirectories(dateDirectory);

        var originalFilename = file.getOriginalFilename();
        var extension = getFileExtension(originalFilename);
        var memeId = UUID.randomUUID();
        var storedName = memeId + extension;
        var filePath = dateDirectory.resolve(storedName);

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var chef = chefRepository.findById(userDetails.getUsername()).orElseThrow(() -> new SecurityException("Access denied"));

        var metadataDTO = uploadMemeDTO.getMemeMetadataDTO();
        var metadata = new MemeMetadata(
                memeId,
                metadataDTO.getTitle(),
                metadataDTO.getDescription(),
                storedName,
                file.getContentType(),
                file.getSize(),
                chef
        );

        memeMetadataRepository.save(metadata);

        return ResponseEntity.ok(new SimpleMessage("Success"));
    }

    private String getFileExtension(String filename) {
        var lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex == -1 ? "" : filename.substring(lastDotIndex);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty())
            throw new IllegalArgumentException("File is empty");
        if (file.getContentType() == null || !memeStorageProperties.getAllowedMimeTypes().contains(file.getContentType()))
            throw new IllegalArgumentException("Invalid mime type");
        if (file.getOriginalFilename() == null)
            throw new IllegalArgumentException("Filename is empty");
    }
}
