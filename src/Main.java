import java.io.*;
import java.util.*;

/** AtCoder 提出用テンプレート。クラス名は Main、default package を維持すること。 */
public class Main {
    static final int MOD = 998_244_353;

    public static void main(String[] args) throws Exception {
        // 再帰が深い問題に備え、大きなスタックのスレッドで実行
        Thread t = new Thread(null, () -> {
            try {
                solve();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }, "main", 1L << 29);
        t.start();
        t.join();
    }

    static void solve() throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        long a = in.nextLong();
        long b = in.nextLong();
        out.println(a + b);

        out.flush();
    }

    /** 高速入力 */
    static final class FastScanner {
        private final InputStream is;
        private final byte[] buf = new byte[1 << 16];
        private int len = 0, ptr = 0;

        FastScanner(InputStream is) { this.is = is; }

        private int read() throws IOException {
            if (ptr == len) {
                len = is.read(buf, 0, buf.length);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }

        int nextInt() throws IOException { return (int) nextLong(); }

        long nextLong() throws IOException {
            int c = read();
            while (c != '-' && (c < '0' || c > '9')) c = read();
            boolean neg = false;
            if (c == '-') { neg = true; c = read(); }
            long r = 0;
            while (c >= '0' && c <= '9') { r = r * 10 + (c - '0'); c = read(); }
            return neg ? -r : r;
        }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        String next() throws IOException {
            int c = read();
            while (c <= ' ') c = read();
            StringBuilder sb = new StringBuilder();
            while (c > ' ') { sb.append((char) c); c = read(); }
            return sb.toString();
        }

        int[] nextInts(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        long[] nextLongs(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }
    }
}
