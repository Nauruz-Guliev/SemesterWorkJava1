package ru.kpfu.itis.gnt.Utils;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;

import static org.apache.commons.codec.digest.DigestUtils.md5;

public class Encrypter {
    public static String md5Hex(final String data) throws IOException {
        return Hex.encodeHexString(md5(data));
    }
}
