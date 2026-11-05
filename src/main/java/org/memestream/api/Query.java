package org.memestream.api;

public interface Query <I, O> {
    O execute(I input);
}
