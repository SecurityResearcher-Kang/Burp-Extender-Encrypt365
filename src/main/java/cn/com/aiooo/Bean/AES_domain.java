package cn.com.aiooo.Bean;

public class AES_domain {
    private int Mode; //加密模式
    private int Padding;//填充方式
    private int DataBlock;//数据块
    private String Password;//密码
    private String Offset;//偏移量
    private int Output;//输出
    private int Charset;//字符集
    private String Aes_encode;//输入框
    private String Aes_decode;//输入框

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    public int getPadding() {
        return Padding;
    }

    public void setPadding(int padding) {
        Padding = padding;
    }

    public int getDataBlock() {
        return DataBlock;
    }

    public void setDataBlock(int dataBlock) {
        DataBlock = dataBlock;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getOffset() {
        return Offset;
    }

    public void setOffset(String offset) {
        Offset = offset;
    }

    public int getOutput() {
        return Output;
    }

    public void setOutput(int output) {
        Output = output;
    }

    public int getCharset() {
        return Charset;
    }

    public void setCharset(int charset) {
        Charset = charset;
    }

    public String getAes_encode() {
        return Aes_encode;
    }

    public void setAes_encode(String aes_encode) {
        Aes_encode = aes_encode;
    }

    public String getAes_decode() {
        return Aes_decode;
    }

    public void setAes_decode(String aes_decode) {
        Aes_decode = aes_decode;
    }
}
