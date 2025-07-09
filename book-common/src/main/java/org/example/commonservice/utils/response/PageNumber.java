package org.example.commonservice.utils.response;

public class PageNumber {

    public static int in(int page) {
        return page - 1;
    }

    public static int out(int page) {
        return page + 1;
    }

}
