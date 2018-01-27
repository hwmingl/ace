package com.fighter.ace.framework.common.remote;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanebert on 16/6/21.
 */
public class RemoteUpload {

    public static String upload(String uploadUrl,List<FileBean> fileBeans){
        String result = "fail";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(uploadUrl);
        try {
            MultipartEntity reqEntity = new MultipartEntity();
            for (FileBean fileBean : fileBeans){
                FileBody fileBody = new FileBody(fileBean.getFile());
                StringBody destDirBody = new StringBody(fileBean.getDestDir());
                reqEntity.addPart("mediaFile",fileBody);
                reqEntity.addPart("filenames",destDirBody);
            }
            httpPost.setEntity(reqEntity);

            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("response code:"+statusCode);
            if (statusCode == HttpStatus.SC_OK){
                System.out.println("successfully");
            }
            InputStream in = response.getEntity().getContent();
            int count = 0;
            while (count == 0) {
                count = Integer.parseInt(""+response.getEntity().getContentLength());
            }
            byte[] bytes = new byte[count];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount <= count) {
                if(readCount == count)break;
                readCount += in.read(bytes, readCount, count - readCount);
            }
            result = new String(bytes,"UTF-8");
            System.out.println("response result:" + result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpPost.releaseConnection();
            httpClient.getConnectionManager().shutdown();
        }
        return result;
    }

    public static void main(String[] args){
        String uploadUrl = "http://192.168.0.116:8080/3dlib/frontcms/common/o_upload_file.do";
        List<FileBean> fileBeans = new ArrayList<>();
        FileBean fileBean1 = new FileBean();
        fileBean1.setFile(new File("/Users/hanebert/Downloads/146003N40-24W.stl"));
        fileBean1.setDestDir("/Users/hanebert/Downloads/");
        fileBeans.add(fileBean1);

        String result = RemoteUpload.upload(uploadUrl,fileBeans);
        System.out.println(result);
    }

}
