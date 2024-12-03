package dev.porthouse;

import java.io.BufferedReader;

import dev.porthouse.utils.FileReader;

public abstract class BaseSolution implements ISolution {

    protected String file;

    protected BufferedReader input;

    public BaseSolution(String file) {
        this.file = file;
        this.input = FileReader.readFile(this.file);
    }

}
