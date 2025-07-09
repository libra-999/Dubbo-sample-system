package org.example.commonservice.enums;

import lombok.Getter;

@Getter
public enum BookStatusConstant {

    PUBLISH("Publish"), PRIVATE("Private") , NON_SALE("Not for sale") ,COMING("Coming Soon");

    private final String message;
    BookStatusConstant(String message) {
        this.message = message;
    }
}
