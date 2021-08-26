package burp.com.burp.assets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DefaultTemplate {
    private static String memorandom_format = "[Format]\n" + "[Target URL]: " + "\n" + "§§pentest_target§§" + "\n\n"
            + "[Referer]: " + "\n" + "§§referred_from§§" + "\n\n" + "[Method]: " + "\n" + "§§method§§" + "\n\n"
            + "[Cookie]: " + "\n" + "§§cookies§§" + "\n\n" + "[GET Parametor]: " + "\n" + "§§get_params§§" + "\n\n"
            + "[POST Parametor]: " + "\n" + "§§body_params§§" + "\n\n" + "[Total Parametor(without Cookies)]: " + "\n"
            + "§§params_count§§";

    /**
     * Burp will load 'template.txt', if the file is exist in same directory.
     * 
     * @throws IOException
     * 
     */
    public static String getFormatTemplate() {
        Path path = Paths.get("./template.txt");
        StringBuffer template = new StringBuffer();
        try (Stream<String> lines = Files.lines(path)) {
            if (Files.size(path) > 1024 * 10240) {
                throw new IOException("[Info]: File size is too large.");
            }
            lines.forEach(lineChar -> template.append(lineChar + "\n"));
        } catch (IOException e) {
            return memorandom_format;
        }
        return new String(template);
    }
}