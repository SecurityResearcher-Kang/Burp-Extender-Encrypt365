package cn.com.aiooo;

import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;

public class Main_UI extends Component {
    public JPanel root;
    private JTabbedPane UI;

    public Main_UI(MontoyaApi api){
        UI.addTab("AES",new AES_UI(api).UI);
        UI.addTab("Hash",new Md5_UI(api).UI);
        UI.addTab("Base编码",new Base_UI(api).Base);

    }

}
