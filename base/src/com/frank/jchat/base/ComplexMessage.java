package com.frank.jchat.base;

public class ComplexMessage extends Message {
    private final SendableFile sendableFile;

    public ComplexMessage(String text, SendableFile sendableFile) {
        super(text);
        this.sendableFile = sendableFile;
    }

    public SendableFile getSendableFile() {
        return sendableFile;
    }
}
