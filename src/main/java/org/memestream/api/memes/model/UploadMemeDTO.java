package org.memestream.api.memes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadMemeDTO {

    private MultipartFile file;
    private MemeMetadataDTO memeMetadataDTO;
}
