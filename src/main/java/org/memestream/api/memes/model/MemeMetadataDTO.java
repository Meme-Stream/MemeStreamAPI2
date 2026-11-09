package org.memestream.api.memes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemeMetadataDTO {
    private String title;
    private String description;
}
