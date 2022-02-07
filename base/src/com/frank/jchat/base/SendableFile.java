package com.frank.jchat.base;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class SendableFile implements Serializable {
    private final byte[] bytes;
    private final String extension;

    public SendableFile(File file) throws IOException {
        this.bytes = Files.readAllBytes(file.toPath());

        String fileName = file.getName();
        this.extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getExtension() {
        return extension;
    }
}