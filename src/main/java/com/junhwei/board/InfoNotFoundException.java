package com.junhwei.board;

//Page not find exeption
public class InfoNotFoundException extends RuntimeException {
    InfoNotFoundException(Long id){ super("Couldn't find info " + id); }
}
