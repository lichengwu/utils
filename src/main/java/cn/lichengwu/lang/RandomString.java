package cn.lichengwu.lang;

/**
 * generate random {@link String}
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-10-23 下午8:35
 */
final public class RandomString {

    private static final char[] BASE_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            .toCharArray();

    /**
     * get the random string for the given length
     * 
     * @param length
     *            the string length
     * @return a random string
     */
    public static String get(int length) {
        Assert.isTrue(length > 0, "String length must greater than 0");
        StringBuilder str = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            str.append(BASE_STRING[Random.rangeRandom(0, BASE_STRING.length)]);
        }
        return str.toString();
    }
}
