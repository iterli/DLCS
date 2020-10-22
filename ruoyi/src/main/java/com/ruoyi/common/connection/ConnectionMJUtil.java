package com.ruoyi.common.connection;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Copyright(C),2020-2020,XXX有限公司
 * Author: lizhendong
 * Date: 2020/10/20,17:29
 */
public class ConnectionMJUtil {
    public static String REQ_POST="POST";
    public static String REQ_GET="GET";
    public static String  connectFaceMachine(String sUrl,String bodyJsonStr,String requestMethod) throws Exception{
        System.out.println("请求的URL:"+sUrl.trim() +" 参数值:"+bodyJsonStr);
        URL url = new URL(sUrl);
//		url.openconnection()；方法原形：
//		public URLConnection openConnection()
//		返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
//		每次调用此 URL 的协议处理程序的 openConnection 方法都打开一个新的连接。
//		url对象用openconnection()打开连接；获得URLConnection类对象，再用URLConnection类对象的connect（）
        HttpURLConnection resumeConnection = (HttpURLConnection) url.openConnection();
        //设置请求头
        //resumeConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        resumeConnection.setRequestProperty("Accept", "application/json");
        //请求的方法
        if (requestMethod.equals(REQ_POST)){
//			httpUrlConnection.setDoOutput(false);以后就可以使用conn.getOutputStream().write()
//			httpUrlConnection.setDoInput(true);以后就可以使用conn.getInputStream().read();
            resumeConnection.setDoOutput(true);
        }
        resumeConnection.setRequestMethod(requestMethod);
        if (bodyJsonStr!=null){
//			getOutputStream方法用于返回Servlet引擎创建的字节输出流对象，Servlet程序可以按字节形式输出响应正文。
//			getWriter方法用于返回Servlet引擎创建的字符输出流对象，Servlet程序可以按字符形式输出响应正文。
//			getOutputStream和getWriter这两个方法互相排斥，调用了其中的任何一个方法后，就不能再调用另一方法。
//			getOutputStream方法返回的字节输出流对象的类型为ServletOutputStream，它可以直接输出字节数组中的二进制数据。
//			getWriter方法将Servlet引擎的数据缓冲区包装成PrintWriter类型的字符输出流对象后返回，PrintWriter对象可以直接输出字符文本内容。
//			Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎获取，Servlet引擎将这些数据当作响应消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。
//			Serlvet的service方法结束后，Servlet引擎将检查getWriter或getOutputStream方法返回的输出流对象是否已经调用过close方法，如果没有，Servlet引擎将调用close方法关闭该输出流对象。
            OutputStream os = resumeConnection.getOutputStream();
	       /* OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
	        osw.write(bodyJsonStr); */
	       /* osw.flush();
	        osw.close(); */
            os.write(bodyJsonStr.trim().getBytes("utf-8"));
            os.flush();
            os.close(); //不要忘记关闭OutputStream
        }
        InputStream urlStream = resumeConnection.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while( (len = urlStream.read(buf))!=-1){
            bos.write(buf, 0, len);
        }
        String jsonStr =  new String(bos.toByteArray(),"utf-8");
        System.out.println("解码后的字符串:"+jsonStr);
        bos.close();
        urlStream.close();
        return jsonStr;
    }

    /**
     * 连接到门禁系统，交互数据
     * @param sUrl
     * @param bodyJsonStr
     * @return
     * @throws Exception
     */
    public static String  connectMJ(String sUrl,String bodyJsonStr,String requestMethod) throws Exception{
        URL url = new URL(sUrl);
        HttpURLConnection resumeConnection = (HttpURLConnection) url.openConnection();
        //设置请求头
        resumeConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        resumeConnection.setRequestProperty("Accept", "application/json");
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;

        // Post 请求不能使用缓存
        resumeConnection.setUseCaches(false);
        resumeConnection.setDoOutput(true);
        resumeConnection.setRequestMethod(requestMethod);
        if (bodyJsonStr!=null){
            OutputStream os = resumeConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"UTF-8");
            osw.write(bodyJsonStr);
            osw.flush();
            osw.close();
            os.close(); //不要忘记关闭OutputStream
        }
        InputStream urlStream = resumeConnection.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while( (len = urlStream.read(buf))!=-1){
            bos.write(buf, 0, len);
        }
     /*   BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss = null;
        String total = "";
        while ((ss = bufferedReader.readLine()) != null) {
            total += ss;
        }*/
        String jsonStr =  new String(bos.toByteArray(),"utf-8");
        System.out.println("解码后的字符串:"+jsonStr);
        bos.close();
        urlStream.close();
        return jsonStr;
    }
}
