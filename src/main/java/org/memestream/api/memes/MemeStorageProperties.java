package org.memestream.api.memes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "app.meme-storage")
@RequiredArgsConstructor
@Getter
public class MemeStorageProperties {
    private final String basePath;
    private final Set<String> allowedMimeTypes;
}
