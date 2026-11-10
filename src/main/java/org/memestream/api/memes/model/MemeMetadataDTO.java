package org.memestream.api.memes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemeMetadataDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "id")
    private String memeId;

    private String title;

    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant creationTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String chefUsername;

}
