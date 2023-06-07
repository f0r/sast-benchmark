package com.murphysec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    // 过程内分析
    @GetMapping("/exec")
    public String exec(@RequestParam(value = "cmd", defaultValue = "") String cmd) throws IOException {
        return Runtime.getRuntime().exec(cmd).toString();
    }

    // 包含净化代码
    @GetMapping("/safe/exec")
    public String safeExec(@RequestParam(value = "cmd", defaultValue = "") String cmd) throws IOException {
        if ("ls" != cmd) {
            return "not allow command";
        }
        return Runtime.getRuntime().exec(cmd).toString();
    }

    private boolean cmdSanitize(String cmd) {
        List<String> allowedCmd = Arrays.asList("ls", "ifconfig");
        return allowedCmd.contains(cmd);
    }

    // 包含净化代码
    @GetMapping("/safe/exec2")
    public String safeExec2(@RequestParam(value = "cmd", defaultValue = "") String cmd) throws IOException {
        if (cmdSanitize(cmd)) {
            return Runtime.getRuntime().exec(cmd).toString();
        } else {
            return "not allow command";
        }
    }

    // 多文件、过程间分析
    @GetMapping("/execCmd")
    public String execCmd(@RequestParam(value = "cmd", defaultValue = "") String cmd) throws IOException {
        Command command = new Command();
        return command.run(cmd);
    }
}