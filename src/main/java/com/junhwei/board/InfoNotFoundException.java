package com.junhwei.board;

public class InfoNotFoundException extends RuntimeException {
    InfoNotFoundException(Long id){ super("Couldn't find info " + id); }
}
