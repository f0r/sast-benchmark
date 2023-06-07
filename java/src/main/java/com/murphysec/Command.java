package com.murphysec;

import java.io.IOException;

public class Command {

    public String run(String cmd) throws IOException {
        return Runtime.getRuntime().exec(cmd).toString();
    }
}
