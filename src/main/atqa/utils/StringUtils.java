package atqa.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static atqa.utils.Invariants.mustNotBeNull;

/**
 * Some simple helper methods for Strings.
 */
public class StringUtils {

    private StringUtils() {
        // using a private constructor to hide the implicit public one.
    }


    /**
     * Returns text that has three symbols replaced -
     * the less-than, greater-than, and ampersand.
     * See <a href="https://www.w3.org/International/questions/qa-escapes#use">...</a>
     * <br>
     * <pre>{@code
     * This will protect against something like <div>$USERNAME</div> allowing
     * a username of
     *      <script>alert(1)</script>
     * becoming
     *      <div><script>alert(1)</script</div>
     * and instead becomes
     *      <div>&lt;script&gt;alert(1)&lt;/script&gt;</div>
     * }</pre>
     * If the text is going inside an attribute (e.g. {@code <div class="TEXT_GOES_HERE">} )
     * Then you need to escape slightly differently. In that case see [safeAttr]
     */
    public static String safeHtml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
    }

    /**
     * Replace dangerous text that would go inside an HTML attribute.
     * See {@link #safeHtml(String)}
     * <br><br>
     * If we get a null string, just return an empty string
     * <br><br>
     * <pre>{@code
     * example:
     *   Given
     *      alert('XSS Attack')
     *   Get
     *      alert(&apos;XSS Attack&apos;)
     * }</pre>
     */
    public static String safeAttr(String input) {
        if (input == null) {
            return "";
        }
        return input
            .replace("\"", "&quot;")
            .replace("'", "&apos;");
    }

    /**
     * Encodes UTF-8 text using URL-encoding
     */
    public static String encode(String str) {
        if (str == null) {
            return "%NULL%";
        }
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    /**
     * Decodes URL-encoded UTF-8 text... except that we
     * first check if the string value is the token %NULL%,
     * which is our way to signify null.
     */
    public static String decode(String str) {
        mustNotBeNull(str);
        if (str.equals("%NULL%")) {
            return null;
        }
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

    public static String generateSecureRandomString(int length) {
        final var allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final var sr = new SecureRandom();

        return IntStream.range(1, length+1)
                .mapToObj (x -> allowedChars.charAt(sr.nextInt(allowedChars.length())))
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    /**
     * Converts a list of bytes to a string. Returns null if the input is null.
     */
    public static String byteListToString(List<Byte> byteList) {
        if (byteList == null) return null;
        final int size = byteList.size();
        final var buf = new byte[size];
        for (int i = 0; i < size; i++) {
            buf[i] = byteList.get(i);
        }
        return new String(buf);
    }

    /**
     * Converts an array of bytes to a string. Returns null if the input is null.
     */
    public static String byteArrayToString(byte[] byteArray) {
        if (byteArray == null) return null;
        return new String(byteArray);
    }

    /**
     * Splits up a string into tokens.
     * @param serializedText the string we are splitting up
     * @param delimiter the character acting as a boundary between sections
     * @return a list of strings.  If the delimiter is not found, we will just return the whole string
     */
    public static List<String> tokenizer(String serializedText, char delimiter) {
        final var resultList = new ArrayList<String>();
        var currentPlace = 0;
        while(true) {
            final var nextPipeSymbolIndex = serializedText.indexOf(delimiter, currentPlace);
            if (nextPipeSymbolIndex == -1) {
                // if we don't see any pipe symbols ahead, grab the rest of the text from our current place
                resultList.add(serializedText.substring(currentPlace));
                break;
            }
            resultList.add(serializedText.substring(currentPlace, nextPipeSymbolIndex));
            currentPlace = nextPipeSymbolIndex + 1;
        }

        return resultList;
    }

}

