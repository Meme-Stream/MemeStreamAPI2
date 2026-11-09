package org.memestream.api;

import java.io.IOException;

public interface Command <I, O> {
    O execute(I input) throws IOException;
}
