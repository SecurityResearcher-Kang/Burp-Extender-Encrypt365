package cn.com.aiooo;

import burp.api.montoya.MontoyaApi;
import cn.com.aiooo.Bean.AES_domain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AES_UI {
    public JPanel UI;
    private JComboBox<String> Mode;
    private JComboBox<String> Padding;
    private JComboBox<String> DataBlock;
    private JTextField Password;
    private JTextField Offset;
    private JComboBox<String> Output;
    private JComboBox<String> Charset;
    private JTextArea Aes_encode;
    private JTextArea Aes_decode;
    private JButton AES_Encode_Button;
    private JButton AES_Decode_Button;

    public AES_UI(MontoyaApi api) {
        init();
        AES_domain aes = new AES_domain();
        aes.setAes_encode(Aes_encode.getText());
        aes.setAes_decode(Aes_decode.getText());
        aes.setMode(Mode.getSelectedIndex());
        aes.setPadding(Padding.getSelectedIndex());
        aes.setDataBlock(DataBlock.getSelectedIndex());
        aes.setOutput(Output.getSelectedIndex());
        aes.setCharset(Charset.getSelectedIndex());
        aes.setPassword(Password.getText());
        aes.setOffset(Offset.getText());

        this.AES_Encode_Button.addActionListener((e) -> {
            
        });
        this.AES_Decode_Button.addActionListener((e) -> {

        });
    }

    private String modeSelected(AES_domain aes){
        switch (aes.getMode()){
            case 0:
                break;
            case 1:
                break;
        }
        return null;
    }

    private void init(){
        String[] mode_str = new String[]{"ECB","CBC","CTR","OFB","CFB"};
        String[] padding_str = new String[]{"pkcs5padding","pkcs7padding","zeropadding","iso10126","ansix923","no padding"};
        String[] dataBlock_str = new String[]{"128位","192位","256位"};
        String[] output_str = new String[]{"base64","hex"};
        String[] charset_str = new String[]{"gb2312编码(简体)","gbk编码(简繁体)","gb18030编码(中日韩)","utf8编码(unicode编码)","iso-8859-1(单字节)"};
        for (String value : mode_str) {
            Mode.addItem(value);
        }
        for (String value : padding_str) {
            Padding.addItem(value);
        }
        for (String value : dataBlock_str) {
            DataBlock.addItem(value);
        }
        for (String value : output_str) {
            Output.addItem(value);
        }
        for (String value : charset_str) {
            Charset.addItem(value);
        }
        Password.setForeground(Color.gray); //将提示文字设置为灰色
        Password.setText("请输入密码");     //显示提示文字
        Password.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (Password.getText().equals("请输入密码")){
                    Password.setText("");     //将提示文字清空
                    Password.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }
                @Override
                public void focusLost(FocusEvent e) {
                    //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                    if (Password.getText().equals("")){
                        Password.setForeground(Color.gray); //将提示文字设置为灰色
                        Password.setText("请输入密码");     //显示提示文字
                    }
                }
        });
        Offset.setForeground(Color.gray); //将提示文字设置为灰色
        Offset.setText("iv偏移量，ecb模式不用填写！");     //显示提示文字
        Offset.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
                if (Offset.getText().equals("iv偏移量，ecb模式不用填写！")){
                    Offset.setText("");     //将提示文字清空
                    Offset.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
                if (Offset.getText().equals("")){
                    Offset.setForeground(Color.gray); //将提示文字设置为灰色
                    Offset.setText("iv偏移量，ecb模式不用填写！");     //显示提示文字
                }
            }
        });
    }
}
