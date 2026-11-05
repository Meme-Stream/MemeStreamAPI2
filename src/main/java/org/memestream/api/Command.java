package org.memestream.api;

public interface Command <I, O> {
    O execute(I input);
}
