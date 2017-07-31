package opensmpp.experiment;

public interface Util {
	
	char[] hexChar = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    static String toHexString(int value) {
        return conventBytesToHexString(intToBytes(value));
    }
    
    /**
     * Convert integer (4 octets) value to bytes.
     * 
     * @param value as 4 bytes representing integer in bytes.
     * @return
     */
    static byte[] intToBytes(int value) {
        byte[] result = new byte[4];
        result[0] = (byte)(value >> 24 & 0xff);
        result[1] = (byte)(value >> 16 & 0xff);
        result[2] = (byte)(value >> 8 & 0xff);
        result[3] = (byte)(value & 0xff);
        return result;
    }
    
    static String conventBytesToHexString(byte[] data) {
        return convertBytesToHexString(data, 0, data.length);
    }

    /**
     * Convert bytes to hex string value (using Big-Endian rule).
     * 
     * @param data is the bytes.
     * @param offset is the offset.
     * @param length is the length.
     * @return the hex string representation of bytes.
     */
    static String convertBytesToHexString(byte[] data, int offset,
            int length) {
        return convertBytesToHexString(data, offset, length, "");
    }
    
    static String convertBytesToHexString(byte[] data, int offset, int length, String byteDelimiter) {
        final StringBuilder stringBuilder = new StringBuilder((length-offset)*(2+byteDelimiter.length()));
        for (int i = offset; i < length; i++) {
            stringBuilder.append(hexChar[(data[i] >> 4) & 0x0f]);
            stringBuilder.append(hexChar[data[i] & 0x0f]);
            stringBuilder.append(byteDelimiter);
        }
        return stringBuilder.toString();
    }
}
