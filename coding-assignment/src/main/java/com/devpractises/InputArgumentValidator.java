package com.devpractises;

import java.util.function.Predicate;

public class InputArgumentValidator implements Predicate<String> {

    @Override
    public boolean test(String args) {

        boolean validPath = args.matches(".*?\\.txt.*");
        return validPath;
    }
}
