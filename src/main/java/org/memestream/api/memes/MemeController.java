package org.memestream.api.memes;

import lombok.RequiredArgsConstructor;
import org.memestream.api.memes.commandhandlers.CreateMemeCommandHandler;
import org.memestream.api.memes.model.MemeMetadataDTO;
import org.memestream.api.memes.model.UploadMemeDTO;
import org.memestream.api.model.SimpleMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/memes")
@RequiredArgsConstructor
public class MemeController {

    private final CreateMemeCommandHandler createMemeCommandHandler;

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessage> createMeme
            (@RequestPart("file")MultipartFile file,
             @RequestPart("metadata") MemeMetadataDTO memeMetadataDTO) throws IOException {
        return createMemeCommandHandler.execute(new UploadMemeDTO(file, memeMetadataDTO));
    }
}
