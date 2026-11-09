package org.memestream.api;

import java.net.MalformedURLException;

public interface Query <I, O> {
    O execute(I input) throws MalformedURLException;
}
