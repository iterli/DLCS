package com.ruoyi.common.json;

public class JsonInvoiceResult {
    //{"result":1,"success":ture} // 请注意，这是一个字符串，是'ture'而不是'true'1
    int result;
    String success;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "\r\nJsonInvoiceResult{" +
                "result=" + result +
                ", success=" + success +"}";
    }
}
