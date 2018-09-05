package continuum.cucumber.PageKafkaExecutorsHelpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;
import java.util.Random;

public class ValuesGenerator {

    public static final String EMAIL = "@email.com";
    private static final int NUMBERS_LENGTH = 10;
    private static Random random = new Random();

    private ValuesGenerator(){}

    public static  String generateEmail(int length) {
        return generateStringWithNumbers(length) + EMAIL;
    }

    /**
     * Generate random string with numbers with current length.
     */
    public static String generateStringWithNumbers(int length) {
        return RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(length - 1);
    }

    /**
     * Generate random string with 10 characters length.
     */
    public static String generateString() {
        return generateString(NUMBERS_LENGTH);
    }

    /**
     * Generate random string with current length. Always return only lower Case values.
     */
    public static String generateString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase(Locale.ENGLISH);
    }

    /**
     * Generate random string with 10 characters length with {@code String start} at the beginning.
     */
    public static String generateString(String start) {
        return start + generateString();
    }

    /**
     * Generate random number from 0 to set value.
     */
    public static int generateNumber(int value){
        return generateNumber(0, value);
    }

    /**
     * Generate random number in range from start value to end value.
     */
    public static int generateNumber(int start, int end){
        return random.nextInt((end-start) + 1) + start;
    }

    /**
     * Generate random long number in range from start value to end value.
     */
    public static long generateNumber(long start, long end){
        return start + (long)(random.nextDouble()*(end - start));
    }
}
