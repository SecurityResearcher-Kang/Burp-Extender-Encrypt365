package cn.com.aiooo;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

public class Encrypt365_Main implements BurpExtension {
    private MontoyaApi api;
    @Override
    public void initialize(MontoyaApi api)
    {
        this.api = api;
        Logging logging = api.logging();
        api.extension().setName("Encrypt365");
        logging.logToOutput("Author: Kang");
        api.userInterface().registerSuiteTab("Encrypt365", new Main_UI(api).root);
    }

}
