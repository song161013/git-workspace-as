package com.synochip.util;

/**
 * Created by songfei on 2018/7/6
 * Description：工具类
 */
public class Utils {

    /**
     * 把16进制字符串转换成字节数组
     * @return byte[]
     */
    public static byte[] hexStringToByte(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < tmp.length / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 数组转换成十六进制字符串
     * @param src byte数组
     * @return HexString
     */
    public static String bytesToHexString(byte[] src, int size) {
        StringBuilder stringBuilder = new StringBuilder();
//        String ret = "";
        if (src == null || size <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().toUpperCase();
    }
}
