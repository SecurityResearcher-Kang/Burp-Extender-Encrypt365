package cn.com.aiooo;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.math.BigInteger;
import java.util.List;

public class Base_UI {
    public JPanel Base;
    private JComboBox<String> base_ComboBox;
    private JButton encode_Button;
    private JTextArea base_encode;
    private JTextArea base_decode;
    private JButton decode_Button;
    private JScrollPane JS;

    public Base_UI(MontoyaApi api){
        Logging logging = api.logging();
        String[] str = {"Base64编码", "Base32编码", "Base16编码", "Base58编码", "Base91编码"};
        for (String value : str) {
            base_ComboBox.addItem(value);
        }
        int s = base_ComboBox.getSelectedIndex();
        this.encode_Button.addActionListener((e)->{
            String result = "";
            String inputString = this.base_encode.getText();
            switch (s){
                case 0:
                    //Base64
                    result = base64_Encode(inputString);
                    break;
                case 1:
                    //Base32
                    result = base32_Encode(inputString);
                    break;
                case 2:
                    //Base16
                    byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8);
                    result = base16_Encode(inputBytes);
                    break;
                case 3:
                    //Base58
                    result = base58_Encode(inputString.getBytes());
                    break;
                case 4:
                    //Base91
                    result = base91_Encode(inputString.getBytes());
                    break;
            };
            this.base_decode.setText(result);
            this.base_decode.setLineWrap(true);
            this.base_decode.setWrapStyleWord(true);
        });

        this.decode_Button.addActionListener((e)->{
            String result = "";
            byte[] decodedBytes = null;
            String inputString = this.base_encode.getText();
            switch (s){
                case 0:
                    //Base64
                    result = base64_Decode(inputString);
                    break;
                case 1:
                    //Base32
                    decodedBytes = base32_Decode(inputString);
                    result = new String(decodedBytes, StandardCharsets.UTF_8);
                    break;
                case 2:
                    //Base16
                    decodedBytes = base16_Decode(inputString);
                    result = new String(decodedBytes, StandardCharsets.UTF_8);
                    break;
                case 3:
                    //Base58
                    decodedBytes = base58_Decode(inputString);
                    result = new String(decodedBytes, StandardCharsets.UTF_8);
                    break;
                case 4:
                    //Base91
                    decodedBytes = base91_Decode(result);
                    result = new String(decodedBytes, StandardCharsets.UTF_8);
                    break;
            };
            this.base_decode.setText(result);
            this.base_decode.setLineWrap(true);
            this.base_decode.setWrapStyleWord(true);
        });
    }

    private String base64_Encode(String inputString){
        byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8);

        String encodedString = Base64.getEncoder().withoutPadding().encodeToString(inputBytes);
        int mod = inputBytes.length % 3;
        if (mod == 1) {
            encodedString += "==";
        } else if (mod == 2) {
            encodedString += "=";
        }
        return encodedString;
    }
    private String base64_Decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    private static final String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static String base32_Encode(String inputString){
        byte[] bytes = inputString.getBytes(StandardCharsets.UTF_8);
        int i = 0, index = 0, digit = 0;
        int currByte, nextByte;
        StringBuilder base32 = new StringBuilder((bytes.length + 7) * 8 / 5);

        while (i < bytes.length) {
            currByte = (bytes[i] >= 0) ? bytes[i] : (bytes[i] + 256);

            if (index > 3) {
                if ((i + 1) < bytes.length) {
                    nextByte = (bytes[i + 1] >= 0) ? bytes[i + 1] : (bytes[i + 1] + 256);
                } else {
                    nextByte = 0;
                }

                digit = currByte & (0xFF >> index);
                index = (index + 5) % 8;
                digit <<= index;
                digit |= nextByte >> (8 - index);
                i++;
            } else {
                digit = (currByte >> (8 - (index + 5))) & 0x1F;
                index = (index + 5) % 8;
                if (index == 0) {
                    i++;
                }
            }
            base32.append(base32Chars.charAt(digit));
        }

        // add padding if necessary
        int padding = 8 - base32.length() % 8;
        if (padding > 0 && padding < 8) {
            for (int j = 0; j < padding; j++) {
                base32.append("=");
            }
        }

        return base32.toString();
    }
    private static byte[] base32_Decode(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return new byte[0];
        }
        inputString = inputString.replaceAll("[=]+$", "");

        int numBytes = inputString.length() * 5 / 8;
        byte[] bytes = new byte[numBytes];

        byte nextByte = 0, currByte = 0;
        int bitsRemaining = 8, mask = 0, index = 0;

        for (int i = 0; i < inputString.length(); i++) {
            int digit = base32Chars.indexOf(inputString.charAt(i));
            if (digit < 0) {
                throw new IllegalArgumentException("Invalid base32 character: " + inputString.charAt(i));
            }

            if (bitsRemaining > 5) {
                mask = digit << (bitsRemaining - 5);
                currByte = (byte) (currByte | mask);
                bitsRemaining -= 5;
            } else {
                mask = digit >> (5 - bitsRemaining);
                currByte = (byte) (currByte | mask);
                bytes[index++] = currByte;
                currByte = (byte) (digit << (3 + bitsRemaining));
                bitsRemaining += 3;
            }
        }

        if (index != numBytes) {
            throw new IllegalArgumentException("Number of decoded bytes does not match expected number");
        }

        return bytes;
    }

    private static final String HEX_CHARS = "0123456789ABCDEF";
    private static String base16_Encode(byte[] inputBytes) {
        StringBuilder result = new StringBuilder(2 * inputBytes.length);

        for (byte b : inputBytes) {
            result.append(HEX_CHARS.charAt((b >> 4) & 0xF))
                    .append(HEX_CHARS.charAt((b & 0xF)));
        }

        return result.toString();
    }
    private static byte[] base16_Decode(String inputString) {
        if (inputString.length() % 2 != 0) {
            throw new IllegalArgumentException("Input string length must be even");
        }

        byte[] result = new byte[inputString.length() / 2];

        for (int i = 0; i < inputString.length(); i += 2) {
            int firstCharIndex = HEX_CHARS.indexOf(Character.toUpperCase(inputString.charAt(i)));
            int secondCharIndex = HEX_CHARS.indexOf(Character.toUpperCase(inputString.charAt(i + 1)));

            if (firstCharIndex < 0 || secondCharIndex < 0) {
                throw new IllegalArgumentException("Invalid input string");
            }

            result[i / 2] = (byte) ((firstCharIndex << 4) | secondCharIndex);
        }

        return result;
    }

    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger BASE = BigInteger.valueOf(58);
    private static String base58_Encode(byte[] inputString){
        if (inputString.length == 0) {
            return "";
        }
        BigInteger num = new BigInteger(1, inputString);
        StringBuilder sb = new StringBuilder();
        while (num.signum() > 0) {
            BigInteger[] quotientAndRemainder = num.divideAndRemainder(BASE);
            sb.append(ALPHABET.charAt(quotientAndRemainder[1].intValue()));
            num = quotientAndRemainder[0];
        }
        for (int i = 0; i < inputString.length && inputString[i] == 0; i++) {
            sb.append(ALPHABET.charAt(0));
        }
        return sb.reverse().toString();
    }
    private static byte[] base58_Decode(String inputString){
        if (inputString.length() == 0) {
            return new byte[0];
        }
        BigInteger num = BigInteger.ZERO;
        for (int i = 0; i < inputString.length(); i++) {
            int index = ALPHABET.indexOf(inputString.charAt(i));
            if (index < 0) {
                throw new IllegalArgumentException("Invalid character " + inputString.charAt(i) + " at index " + i);
            }
            num = num.multiply(BASE).add(BigInteger.valueOf(index));
        }
        byte[] bytes = num.toByteArray();
        if (bytes.length == 0) {
            return new byte[]{0};
        }
        int leadingZeros = 0;
        while (leadingZeros < inputString.length() && inputString.charAt(leadingZeros) == ALPHABET.charAt(0)) {
            leadingZeros++;
        }
        byte[] decoded = new byte[bytes.length + leadingZeros];
        System.arraycopy(bytes, 0, decoded, leadingZeros, bytes.length);
        return decoded;
    }

    // 用于加密的字符表
    private static final String BASE91_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&()*+,./:;<=>?@[]^_`{|}~\"";
    // 用于解密的字符表
    private static final Map<Character, Integer> DECODE_MAP = new HashMap<Character, Integer>();
    static {
        // 初始化解密字符表
        for (int i = 0; i < BASE91_CHARS.length(); i++) {
            DECODE_MAP.put(BASE91_CHARS.charAt(i), i);
        }
    }
    private static String base91_Encode(byte[] inputString) {
        StringBuilder sb = new StringBuilder();
        long buffer = 0;
        int bitsLeft = 0;
        for (int i = 0; i < inputString.length; i++) {
            buffer |= (inputString[i] & 0xff) << bitsLeft;
            bitsLeft += 8;
            if (bitsLeft > 13) {
                int code = (int) (buffer & 0x1fff);
                buffer >>= 13;
                bitsLeft -= 13;
                sb.append(BASE91_CHARS.charAt(code % 91));
                sb.append(BASE91_CHARS.charAt(code / 91));
            }
        }
        if (bitsLeft != 0) {
            int code = (int) (buffer & 0x1fff);
            sb.append(BASE91_CHARS.charAt(code % 91));
            if (bitsLeft > 7 || code > 90) {
                sb.append(BASE91_CHARS.charAt(code / 91));
            }
        }
        return sb.toString();
    }
    private static byte[] base91_Decode(String inputString) {
        int length = inputString.length();
        if (length == 0) {
            return new byte[0];
        }
        List<Byte> buffer = new ArrayList<Byte>();
        long b = 0;
        int n = 0;
        int v = -1;
        for (int i = 0; i < length; i++) {
            char c = inputString.charAt(i);
            if (!DECODE_MAP.containsKey(c)) {
                continue;
            }
            if (v < 0) {
                v = DECODE_MAP.get(c);
            } else {
                v += DECODE_MAP.get(c) * 91;
                b |= v << n;
                n += (v & 8191) > 88 ? 13 : 14;
                do {
                    buffer.add((byte) b);
                    b >>= 8;
                    n -= 8;
                } while (n > 7);
                v = -1;
            }
        }
        if (v + 1 != 0) {
            buffer.add((byte) ((b | v << n) & 0xff));
        }
        byte[] result = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            result[i] = buffer.get(i);
        }
        return result;
    }

}
