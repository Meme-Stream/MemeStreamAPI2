package org.memestream.api.memes;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Set;

@ConfigurationProperties(prefix = "app.meme-storage")
@RequiredArgsConstructor(onConstructor_ = @ConstructorBinding)
@Getter
public class MemeStorageProperties {
    private final String basePath;
    private final Set<String> allowedMimeTypes;
}
