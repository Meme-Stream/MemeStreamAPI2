package org.memestream.api.memes;

import lombok.RequiredArgsConstructor;
import org.memestream.api.memes.commandhandlers.CreateMemeCommandHandler;
import org.memestream.api.memes.model.MemeMetadataDTO;
import org.memestream.api.memes.model.UploadMemeDTO;
import org.memestream.api.memes.queryhandlers.GetAllMemesQueryHandler;
import org.memestream.api.memes.queryhandlers.GetMemeByIdQueryHandler;
import org.memestream.api.model.SimpleMessage;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/memes")
@RequiredArgsConstructor
public class MemeController {

    private final CreateMemeCommandHandler createMemeCommandHandler;

    private final GetMemeByIdQueryHandler getMemeByIdQueryHandler;

    private final GetAllMemesQueryHandler getAllMemesQueryHandler;

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessage> createMeme
            (@RequestPart("file") MultipartFile file,
             @RequestPart("metadata") MemeMetadataDTO memeMetadataDTO) throws IOException {
        return createMemeCommandHandler.execute(new UploadMemeDTO(file, memeMetadataDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Resource> getMemeById(@PathVariable(name = "uuid") String id) throws MalformedURLException {
        return getMemeByIdQueryHandler.execute(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemeMetadataDTO>> getAllMemes
            (@PageableDefault(size = 20, sort = "creationTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return getAllMemesQueryHandler.execute(pageable);
    }
}
