import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** 簡易ジャッジ。使い方: java Judge.java <classpath> <testsDir> <java> <javaFlags> */
public class Judge {
    public static void main(String[] args) throws Exception {
        String cp = args[0];
        Path dir = Paths.get(args[1]);
        String java = args.length > 2 ? args[2] : "java";
        List<String> flags = args.length > 3 && !args[3].isBlank()
                ? Arrays.asList(args[3].trim().split("\\s+")) : List.of();

        List<Path> ins = new ArrayList<>();
        try (var s = Files.list(dir)) {
            s.filter(p -> p.toString().endsWith(".in")).sorted().forEach(ins::add);
        }
        int ng = 0;
        for (Path in : ins) {
            String name = in.getFileName().toString().replaceFirst("\\.in$", "");
            Path expFile = dir.resolve(name + ".out");
            List<String> cmd = new ArrayList<>(List.of(java));
            cmd.addAll(flags);
            cmd.addAll(List.of("-cp", cp, "Main"));
            Path actFile = Files.createTempFile("judge", ".out");
            long t0 = System.nanoTime();
            Process p = new ProcessBuilder(cmd)
                    .redirectInput(in.toFile())
                    .redirectOutput(actFile.toFile())
                    .redirectError(ProcessBuilder.Redirect.INHERIT)
                    .start();
            boolean done = p.waitFor(10, TimeUnit.SECONDS);
            if (!done) p.destroyForcibly();
            long ms = (System.nanoTime() - t0) / 1_000_000;
            String status;
            if (!done) status = "TLE";
            else if (p.exitValue() != 0) status = "RE";
            else if (!Files.exists(expFile)) status = "NO_EXPECTED";
            else status = norm(Files.readString(actFile, StandardCharsets.UTF_8))
                    .equals(norm(Files.readString(expFile, StandardCharsets.UTF_8))) ? "AC" : "WA";
            if (!status.equals("AC")) ng++;
            System.out.printf("[%s] %s (%d ms)%n", status, name, ms);
            if (status.equals("WA")) {
                System.out.println("  expected: " + norm(Files.readString(expFile)).replace("\n", "\n            "));
                System.out.println("  actual  : " + norm(Files.readString(actFile)).replace("\n", "\n            "));
            }
            Files.deleteIfExists(actFile);
        }
        System.out.printf("%d/%d passed%n", ins.size() - ng, ins.size());
        System.exit(ng == 0 ? 0 : 1);
    }

    /** 行末空白・末尾改行・CRLF の差を無視 */
    static String norm(String s) {
        String[] lines = s.replace("\r\n", "\n").split("\n", -1);
        StringBuilder sb = new StringBuilder();
        for (String l : lines) sb.append(l.stripTrailing()).append('\n');
        return sb.toString().stripTrailing();
    }
}
