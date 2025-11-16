package com.example.demo.file.model;

import lombok.Getter;

@Getter
public enum FileDirectoryEnum {
    AVATAR("avatar"), FILE("file");
    private final String dirName;

    FileDirectoryEnum(String dirName) {
        this.dirName = dirName;
    }
}
