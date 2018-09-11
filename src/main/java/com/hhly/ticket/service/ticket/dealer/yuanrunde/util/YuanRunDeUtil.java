package com.hhly.ticket.service.ticket.dealer.yuanrunde.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;

import com.hhly.ticket.util.HttpUtil;

public class YuanRunDeUtil {
	
	private static String CHARSET = "UTF-8";
	
	public static final Map<String, String> ERROR_CODE;
	
	static {
		ERROR_CODE = new HashMap<>();
		ERROR_CODE.put("0", "操作成功");
		ERROR_CODE.put("1", "处理中");
		ERROR_CODE.put("2", "失败");
		ERROR_CODE.put("9001", "暂停销售");
		ERROR_CODE.put("9002", "查询结果不存在");
		ERROR_CODE.put("9003", "订单号重复");
		ERROR_CODE.put("9004", "彩种错误");
		ERROR_CODE.put("9005", "期号错误");
		ERROR_CODE.put("9006", "彩期不存在");
		ERROR_CODE.put("9007", "彩期未开售");
		ERROR_CODE.put("9008", "金额错误");
		ERROR_CODE.put("9009", "注码格式错误");
		ERROR_CODE.put("90010", "商户账户不存在");
		ERROR_CODE.put("90011", "商户可用余额不足");
		ERROR_CODE.put("90012", "投注订单错误");
		ERROR_CODE.put("90013", "场次不存在");
		ERROR_CODE.put("90014", "请求间隔小于一秒");
		ERROR_CODE.put("90015", "解密错误");
		ERROR_CODE.put("90016", "解析错误");
		ERROR_CODE.put("90017", "请求命令错误");
		ERROR_CODE.put("90018", "场次对应玩法未开售");
	}
	
	/**
	 * 对message进行des加密
	 * @param deskey秘钥
	 * @param message
	 * @return
	 */
	public static String desEncrypt(String message,String key) {
		String encryptStr = null;
		byte encryptByte[];

		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, genSecretKey(key));
			byte[] cipherText = cipher
					.doFinal(message.trim().getBytes(CHARSET));
			encryptByte = Base64.encodeBase64(cipherText);
			encryptStr = new String(encryptByte, "UTF-8");
		} catch (Throwable e) {
			throw new RuntimeException("des加密发生异常", e);
		}

		return encryptStr;
	}

	/**
	 * 对cipherText进行des解密
	 * 
	 * @param cipherText
	 * @return
	 */
	public static String desDecrypt(String cipherText,String key) {
		String decryptStr = null;

		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			byte[] input = Base64.decodeBase64(cipherText.trim().getBytes(
					"UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, genSecretKey(key));
			byte[] output = cipher.doFinal(input);
			decryptStr = new String(output, CHARSET);
		} catch (Throwable e) {
			throw new RuntimeException("des解密发生异常", e);
		}

		return decryptStr;
	}
	
	
	private static SecretKey genSecretKey(String key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		//DESKeySpec desKeySpec = new DESKeySpec(hexStringToByte(key));
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		return secretKey;
	}
	
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}
	
	/**
     * 发送http请求
     *
     * @param url 请求地址，param请求参数
     * @throws Exception
     */
    public static String post(String url, String param,String charset) throws Exception {

        OutputStreamWriter reqOut = null;

        try {

            HttpURLConnection connection = createConnection(url,"POST",charset,30);
            if (param != null) {
                reqOut = new OutputStreamWriter(connection.getOutputStream(),charset);
                reqOut.write(param);
                reqOut.flush();
            }
            int charCount = -1;
            int status = connection.getResponseCode();
            if (status == HttpStatus.SC_OK) {
            	
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
                StringBuffer responseMessage = new StringBuffer();
                while ((charCount = br.read()) != -1) {
                    responseMessage.append((char) charCount);
                }
                br.close();
                return responseMessage.toString();
            }
           throw  new RuntimeException("请求返回的状态不是200,url="+url+",return status="+status);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (reqOut != null)
                    reqOut.close();
            } catch (IOException e) {

            }
        }

    }

    private static HttpURLConnection createConnection(String url,String method,String encoding,int timeout)
            throws Exception
    {


        URL  urlStr= new URL(url);
        HttpURLConnection   httpURLConnection = (HttpURLConnection)urlStr.openConnection();

        httpURLConnection.setConnectTimeout(timeout*1000);
        httpURLConnection.setReadTimeout(timeout*1000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=" + encoding);
        httpURLConnection.setRequestMethod(method);
        
        return httpURLConnection;
    }
	
	public static void main(String[] args) {
		
		try {
			post("http://localhost:8178/lotto-ticket/channel/yuanrunde/api", "<?xml version=\"1.0\" encoding=\"utf-8\"?><content><head><msgId>shyc153620120180817101020</msgId><merchantCode>shyc1536</merchantCode><commandCode>201</commandCode><timestamp>20180817101020</timestamp></head><body>ePDcykR7lIYxvKQ/APui/fncv/CruSrCyQ/YSJEo9OWvWXLUWFPN72nQIFFHqUyGo0ic6TTQQG3Pdg/rk1ihtdZwR/OcoRZaTeb3 QwyQgrpUTk3xIrIr/4/GuuwqgYc1frCf6tWSbtOjnkuK6ZhCloQWUXicMIX9BlM0NOg1Ga x4R19YS38QdFn0O8Ynnri YULieaqIUM8yBTW0cBeTDv3o6we5G8dmlMOoK5y1CigYMBQSL4/DWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9p0CBRR6lMhgpiYSleBYToz3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/ PxrrsKoGHNX6wn rVkm7To55LiumYQpaEFlF4nDCF/QZTNDToNRmvseEdfWEt/EHRZ9DvGJ564vmFC4nmqiFDPMgU1tHAXkw796OsHuRvHZpTDqCuctQHreii7GMrAw1n xdNWoBJbBjWsBShwIw66cu679nR4DWCcLGSbgJRSdHP45fXe0W dy/8Ku5KsLJD9hIkSj05a9ZctRYU83vadAgUUepTIYuYWPqkFAy 892D uTWKG11nBH85yhFlpN5vf5DDJCCulROTfEisivxqzZPfNaAFZyAEhUbHwyrIGzUnStDt9GWhBZReJwwhf0GUzQ06DUZr7HhHX1hLfxB0WfQ7xieeuL5hQuJ5qohQzzIFNbRwF5MO/ejrB7kbwACNrsh5pxe4S6K4X6YT/DNZ/sXTVqASWwY1rAUocCMOunLuu/Z0eA1gnCxkm4CUUnRz OX13tFvncv/CruSrCyQ/YSJEo9OWvWXLUWFPN72nQIFFHqUyGhHzunIoNbjHPdg/rk1ihtdZwR/OcoRZaTeb3 QwyQgrpUTk3xIrIr8as2T3zWgBWcgBIVGx8MqyBs1J0rQ7fRloQWUXicMIX9BlM0NOg1Ga x4R19YS38QdFn0O8Ynnri YULieaqIUM8yBTW0cBeTDv3o6we5G8dmlMOoK5y1AlXGGY4G0emzWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9p0CBRR6lMhiD2fiGh60nvz3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/ PxrrsKoGHNX6wn rVkm7To55LiumYQovBffNwkrg3y71YThkESOVnWbnvXTOb6JV/e5m6cbiMIy1kq8DH8IW1nvWbrXQ7NK9lEO4JE/giM6pOpZtn5cBmYSdknh1Sa6JMzELGmbtmVi0D2zi0uZByBB/QvlFMX OEPMQKCKyNFz1VAXcvAuHsbFJGh XtY6UgVr5AinOFj9g6ken0MDD9GzEVTF5tiwm3nYYqLB5ZwCK8 ZjIFPYQMjHB0vq9d/aHmgz/YUvMCftNF0m5h3jDwFCd8bp1bfHjoPvrOpI4yIG G853GFTcYAHlIXvOLcZZTf6dl64yk 1AdulzYZHChQuUGrnjLOr81slIYkUBK5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0JF60FaZnZNiQ2ia609Qhzdq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqUr9aDoBKJ 9GHOUeO9NBVVReUShSz ewLl9MmQumUK8cgBIVGx8MqybBj0487EoBbg/FFRidd22FvWamzjvLUdLmGGR3HGLdzKQ1irkRKC6au5iG45UB1GFfby21zAoN N5fK5RMZ6ybFy8IfP13NHsag6WeOPgQQNZfnc914xgKLZ2mnwBT iKBo4YiRgu6k2W8kOISE8AFHlnGDWlpzt YvPwGeOemt9vi8jhSnwantMt4lUm43LlsdlZA5WPSChW4UkpZt9SbJDe42w5Sl5G2iMkNO25QtCn0xH/SNuzdF3fCiHH5rd/eKYyuk/ViDA6Mq2hpj MUAScC32lwv/zWHR7kzovCJKGT4xdEnSLUT9fkD3P9Zhw9RAl/tR9kLncchJPChhC89SBAZHfnnYH6pQzwddkZwwnqWToulxABpf5xqz9vP0xc6d5i4e8ovnqrCAlk49gCzrcxoF/J6corOnDL06SZYMG6SpLNXbgB1ENShLX0AY76Il6kPpk8jF8cQnExZkqebtJmuBbf5kxCPftY6rTGgjy8ZXACYGpuhZAavsTDSS0EXuDXFzFrFF5RKFLP57AGKZdQ8PkP5F/ZEx0Gr5Orz51DrIZXs7rcYAHlIXvOLcZZTf6dl64yk 1AdulzYZHChQuUGrnjLNS6aPDFzS8Ga5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0JF60FaZnZNijVdlWWmkKhdq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqRO6EKPLlbpn0Tyu9or6vw1ReUShSz ewMas2T3zWgBWcgBIVGx8MqyBs1J0rQ7fRloQWUXicMIX9BlM0NOg1Ga x4R19YS38QdFn0O8Ynnri YULieaqIUM8yBTW0cBeTDv3o6we5G8dmlMOoK5y1AJS7SpVdi7eTWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9p0CBRR6lMhippKJpL0 Vyz3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/GrNk981oAVnIASFRsfDKsgbNSdK0O30ZOI9Pda3KFAbMtR3x6L/9HnWbnvXTOb6JV/e5m6cbiMIy1kq8DH8IW1nvWbrXQ7NLs7OC5rrNGKM6pOpZtn5cBmYSdknh1Sa6JMzELGmbtmVi0D2zi0uZByBB/QvlFMX OEPMQKCKyNFz1VAXcvAuHsbFJGh XtY6UgVr5AinOFhskKZL/N1Ys9GzEVTF5tiwm3nYYqLB5ZwCK8 ZjIFPYQMjHB0vq9d 9J6CSJwy0kSg4ElhwbeJcMmCZyTeuQRrHjoPvrOpI48A8kCg2XTvxcYAHlIXvOLcZZTf6dl64yk 1AdulzYZHChQuUGrnjLMtbGE3I9W 6q5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0BZyPwZvDUHT9QI3y8nHtaJq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqRiI7Q7S1dfqqkfVP5X8EBqzf3BuTcs8KTJgmck3rkEax46D76zqSOPAPJAoNl078XGAB5SF7zi3GWU3 nZeuMpPtQHbpc2GRwoULlBq54yzzYlLgKSwgxeuUxlYFhpevybedhiosHlnbFy8IfP13NHhS63JNkohAZftrZA2rAzaSPTtAKbjUoSs5qA80PqjkADNdm5pRjaBhjDpM6M8wNAWcj8Gbw1B08bn7UG3V8fZau5iG45UB1EEcaxTAdpqkcP7qpfoy8KGCPLxlcAJgakYiO0O0tXX6qpH1T V/BAas39wbk3LPCkyYJnJN65BGseOg  s6kjjwDyQKDZdO/FxgAeUhe84txllN/p2XrjKT7UB26XNhkcKFC5QaueMs6vzWyUhiRQErlMZWBYaXr8m3nYYqLB5Z2xcvCHz9dzR4UutyTZKIQGX7a2QNqwM2kj07QCm41KErOagPND6o5AAzXZuaUY2gYYw6TOjPMDQFnI/Bm8NQdOPeCjoPgbfq2ruYhuOVAdRBHGsUwHaapHD 6qX6MvChgjy8ZXACYGpGIjtDtLV1 qqR9U/lfwQGrN/cG5NyzwpMmCZyTeuQRrHjoPvrOpI47jR8yYI/1sOVpqfpDpl3zAZZTf6dl64yk 1AdulzYZHChQuUGrnjLNFzhLMttuqNa5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0BZyPwZvDUHTqrHgp 2R6wtq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqRiI7Q7S1dfqqkfVP5X8EBqzf3BuTcs8KTJgmck3rkEax46D76zqSOO40fMmCP9bDlaan6Q6Zd8wGWU3 nZeuMpPtQHbpc2GRwoULlBq54yzwHLTCpOvWg6uUxlYFhpevybedhiosHlnbFy8IfP13NHhS63JNkohAZftrZA2rAzaSPTtAKbjUoSs5qA80PqjkADNdm5pRjaBhjDpM6M8wNAWcj8Gbw1B00NomutPUIc3au5iG45UB1EEcaxTAdpqkcP7qpfoy8KGCPLxlcAJgakTuhCjy5W6Z9E8rvaK r8NUXlEoUs/nsC5fTJkLplCvHIASFRsfDKsmwY9OPOxKAW4PxRUYnXdthb1mps47y1HS5hhkdxxi3fABRb2uncaxWruYhuOVAdRhX28ttcwKDfjeXyuUTGesmxcvCHz9dzR7GoOlnjj4EEDWX53PdeMYCi2dpp8AU/oigaOGIkYLupNlvJDiEhPAByPZDPcOr6 fmLz8Bnjnprfb4vI4Up8Gp7TLeJVJuNy5bHZWQOVj0hLiS53kT9izfy3NuJOBwcWqb5 L1appWrQp9MR/0jbs3Rd3wohx a3f3imMrpP1YgwOjKtoaY/jFAEnAt9pcL/81h0e5M6Lwgc2DSsXNiQblE/X5A9z/WYcPUQJf7UfZC53HISTwoYQvPUgQGR3552B qUM8HXZGcMJ6lk6LpcQAaX cas/bz9MXOneYuHvKL56qwgJZOPYPA4nFHLJ7htKKzpwy9OkmWDBukqSzV24AdRDUoS19AGO iJepD6ZPIxfHEJxMWZKnm7SZrgW3 ZMQj37WOq0xoI8vGVwAmBqUr9aDoBKJ 9GHOUeO9NBVVlSUF/S5Da2jD5ItZt M/qGW0XWZnKgmfllXP2OHCRgoyxD8PSs2/MgwbpKks1duC5Cg6bmupnwjYQWPHsUzjZudxyEk8KGEIZC1nEcTO1/zVK297wHAhCnHUnf0RMtbkppDR82Pzrn4EeoXbJmSExtaWSbxqjgSJQ9ucDCSWqhszW/g9aVCEjvj0lNb4g7/NPt3O6AQNWWmNfJzm5GW92mzk2ZkFrueY1ZQNGLZmrDihW4UkpZt9SbJDe42w5Sl4bQF0nk0fjnh5ciX9MX7Vq0DwCvURYviuCpk2ZPVtnj kj2KNG3kKnUPbnAwklqoZQO2KjyRsrb9A LjIUNwutXbHPq6C9hzpaXCaqrC9OPFOdGHxJeWDlOeE2ZUpMJ8 O3veAxxk6iCByry9enawHp8v5T0xb0BafpbbnCawXpp7vWDZV8234EoZUQal6bB16mFu91m1Bki8F983CSuDfLvVhOGQRI5W3tk3qLJIkclxJ7WiOk94Y5bMB8x//HSdUuv50wdMcbY/GLdZ8FZhRW7Sm5HGE 092uQhyREnJK0p0Fo5sgeKtuYbmc7QUaFMwwJsdBD vfdYJwsZJuAlFet4VCHtvgHrbDOQs7wCWjMIvApxyydGEYTAL21kPYeUDHMlV S32pM2WgBgtTZKlIxIBIeWUwM9lD7fHb0hhMTWf7F01agElfmLz8BnjnppBOcK47f84lMeOg  s6kjjIgb4bzncYVPglJnUFVpYJ0 3c7oBA1ZaGtsYuIm72B r1gXPvWNx64vmFC4nmqiFDPMgU1tHAXkw796OsHuRvHZpTDqCuctQJVxhmOBtHps1n xdNWoBJbBjWsBShwIw66cu679nR4DWCcLGSbgJRSdHP45fXe0W dy/8Ku5KsLJD9hIkSj05a9ZctRYU83vadAgUUepTIY5QMV3C8yLWs92D uTWKG11nBH85yhFlpN5vf5DDJCCulROTfEisiv/j8a67CqBhzV sJ/q1ZJu06OeS4rpmEKaMSHEYmN561xdt0FkiBp2re2TeoskiRy0HVdz/2wpPprghBn2AkpRVS6/nTB0xxtj8Yt1nwVmFFbtKbkcYT7T3a5CHJESckrakjbGsOYvSy5huZztBRoUzDAmx0EP6991gnCxkm4CUV63hUIe2 AetsM5CzvAJaMwi8CnHLJ0YRhMAvbWQ9h5QMcyVX5LfakzZaAGC1NkqVUV9IAkk8e0WUPt8dvSGExNZ/sXTVqASV YvPwGeOemkE5wrjt/ziUx46D76zqSONAf kUf/RkxeS5Ez/XkmxiT7dzugEDVloa2xi4ibvYH6vWBc 9Y3HrNWUDRi2Zqw5XB2IdTA/iqPiSZUsgM80fG0BdJ5NH454eXIl/TF 1atA8Ar1EWL4rgqZNmT1bZ4/pI9ijRt5Cp1D25wMJJaqGUDtio8kbK2/QPi4yFDcLrV2xz6ugvYc6WlwmqqwvTjxTnRh8SXlg5TnhNmVKTCfPjt73gMcZOoggcq8vXp2sB9giWW/iiqsan6W25wmsF6ae71g2VfNt BKGVEGpemwdephbvdZtQZIvBffNwkrg3y71YThkESOVt7ZN6iySJHJcSe1ojpPeGOWzAfMf/x0ngzI09ccUgXUI8vGVwAmBqQ/kEFXewCFdtBF7g1xcxaxlSUF/S5Da2jD5ItZt M/qGW0XWZnKgme3WiX6/lv9RYyxD8PSs2/MgwbpKks1duC5Cg6bmupnwjYQWPHsUzjZudxyEk8KGEIZC1nEcTO1/zVK297wHAhCnHUnf0RMtbkppDR82Pzrn4EeoXbJmSExONqR3tRCqQRQ9ucDCSWqhszW/g9aVCEjvj0lNb4g7/NPt3O6AQNWWuIhymGHv536B0WfQ7xiees1ZQNGLZmrDihW4UkpZt9SbJDe42w5Sl5G2iMkNO25QtCn0xH/SNuzdF3fCiHH5rd/eKYyuk/ViDA6Mq2hpj MUAScC32lwv/zWHR7kzovCJKGT4xdEnSLUT9fkD3P9Zhw9RAl/tR9kLncchJPChhC89SBAZHfnnYH6pQzwddkZwwnqWToulxABpf5xqz9vP0xc6d5i4e8ovnqrCAlk49goAvtfTO0gp8orOnDL06SZYMG6SpLNXbgB1ENShLX0AY76Il6kPpk8jF8cQnExZkqebtJmuBbf5kxCPftY6rTGgjy8ZXACYGpGIjtDtLV1 qqR9U/lfwQGrN/cG5NyzwpMmCZyTeuQRrHjoPvrOpI48A8kCg2XTvxcYAHlIXvOLcZZTf6dl64yk 1AdulzYZHChQuUGrnjLNS6aPDFzS8Ga5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0NheqhPX3LHLmFy4OVbL lxq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqRO6EKPLlbpn0Tyu9or6vw1ReUShSz ewBimXUPD5D Rf2RMdBq Tq8 dQ6yGV7O6 CUmdQVWlgnT7dzugEDVlqHhK QdTBMmgdFn0O8Ynnri YULieaqIUM8yBTW0cBeTDv3o6we5G8AAja7IeacXsrKcEcXqA3yDWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9p0CBRR6lMhitmAoHXPiA0z3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/GrNk981oAVnIASFRsfDKsgbNSdK0O30ZaEFlF4nDCF/QZTNDToNRmvseEdfWEt/EHRZ9DvGJ56zVlA0YtmasOVwdiHUwP4qj4kmVLIDPNHxtAXSeTR OeHlyJf0xftWrQPAK9RFi K4KmTZk9W2eP6SPYo0beQqdQ9ucDCSWqhlA7YqPJGytv0D4uMhQ3C61dsc roL2HOlpcJqqsL048U50YfEl5YOU54TZlSkwnz47e94DHGTqIIHKvL16drAe9c3caFYCrQZ ltucJrBemnu9YNlXzbfgShlRBqXpsHXqYW73WbUGSLwX3zcJK4N8u9WE4ZBEjlbe2TeoskiRyTEzt2EGTsiAsGUpl1709Xi30zlyX6ZOM60n8Fp58PwJG2iMkNO25QtCn0xH/SNuzdF3fCiHH5rd/eKYyuk/ViDA6Mq2hpj MUAScC32lwv/zWHR7kzovCBzYNKxc2JBuUT9fkD3P9Zhw9RAl/tR9kLncchJPChhC89SBAZHfnnYH6pQzwddkZwwnqWToulxABpf5xqz9vP0xc6d5i4e8ovnqrCAlk49gy/Mq0wohC8worOnDL06SZYMG6SpLNXbgB1ENShLX0AY76Il6kPpk8poyrun5s5FABXo3JhPw2TODMjT1xxSBdQjy8ZXACYGpGIjtDtLV1 qqR9U/lfwQGrN/cG5NyzwpMmCZyTeuQRrHjoPvrOpI47jR8yYI/1sOVpqfpDpl3zAZZTf6dl64yk 1AdulzYZHChQuUGrnjLNS6aPDFzS8Ga5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0NheqhPX3LHLRLgBiR/pEqNq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqboWQGr7Ew0ktBF7g1xcxaxReUShSz ewBimXUPD5D Rf2RMdBq Tq8 dQ6yGV7O6 CUmdQVWlgnT7dzugEDVloa2xi4ibvYH6vWBc 9Y3Hri YULieaqIUM8yBTW0cBeTDv3o6we5G8dmlMOoK5y1DuPvAemXb5WzWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9p0CBRR6lMhsyfCvCvNd5nz3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/ PxrrsKoGHNX6wn rVkm7To55LiumYQovBffNwkrg3y71YThkESOVt7ZN6iySJHLQdV3P/bCk muCEGfYCSlFVLr dMHTHG2Pxi3WfBWYUVu0puRxhPtPdrkIckRJySuuBgsvMmeO6rmG5nO0FGhTMMCbHQQ/r33WCcLGSbgJRXreFQh7b4B62wzkLO8AlozCLwKccsnRhGEwC9tZD2HlAxzJVfkt9qTNloAYLU2SpXlaSxe6tlsIZQ 3x29IYTE1n xdNWoBJX5i8/AZ456aQTnCuO3/OJTHjoPvrOpI40B/6RR/9GTF5LkTP9eSbGJPt3O6AQNWWuIhymGHv536B0WfQ7xiees1ZQNGLZmrDlcHYh1MD Ko JJlSyAzzR8bQF0nk0fjnh5ciX9MX7Vq0DwCvURYviuCpk2ZPVtnj Kk9TRb3d8UUPbnAwklqoZQO2KjyRsrb9A LjIUNwutXbHPq6C9hzpaXCaqrC9OPFOdGHxJeWDlOeE2ZUpMJ8 O3veAxxk6iCByry9enawH8k6xztuUfQqfpbbnCawXpp7vWDZV8234EoZUQal6bB16mFu91m1Bkt/XQmh0dmK8s75OJRaNbW58Sjz5Xeovkpoyrun5s5FABXo3JhPw2TODMjT1xxSBdQjy8ZXACYGpSv1oOgEon70Yc5R4700FVWVJQX9LkNraMPki1m34z oZbRdZmcqCZ4AGOjLPg7aDjLEPw9Kzb8yDBukqSzV24LkKDpua6mfCNhBY8exTONm53HISTwoYQhkLWcRxM7X/NUrb3vAcCEKcdSd/REy1uSmkNHzY/OufgR6hdsmZITFv5kybP4JJ01D25wMJJaqGzNb D1pUISO PSU1viDv80 3c7oBA1ZaY18nObkZb3abOTZmQWu55jVlA0YtmasOpC4FmHOqOh5/poUfZJ 7oUbaIyQ07blCRQuVKWVjKFvR83DaEgm1kX94pjK6T9WIMDoyraGmP4xQBJwLfaXC//NYdHuTOi8Ip8n4Zxvn2sFRP1 QPc/1mHD1ECX 1H2QudxyEk8KGELz1IEBkd edgfqlDPB12RnDCepZOi6XEAGl/nGrP28/TFzp3mLh7yi eqsICWTj2BJkKYv3vhvkiis6cMvTpJlgwbpKks1duAHUQ1KEtfQBjvoiXqQ mTyMXxxCcTFmSp5u0ma4Ft/mTEI9 1jqtMaCPLxlcAJgakYiO0O0tXX6qpH1T V/BAas39wbk3LPCkyYJnJN65BGseOg  s6kjjuNHzJgj/Ww5Wmp kOmXfMBllN/p2XrjKT7UB26XNhkcKFC5QaueMs/gpcsmVCrKTrlMZWBYaXr8m3nYYqLB5Z2xcvCHz9dzR4UutyTZKIQGX7a2QNqwM2kj07QCm41KErOagPND6o5AAzXZuaUY2gYYw6TOjPMDQSep3dA1gsRCYXLg5Vsv6XGruYhuOVAdRBHGsUwHaapHD 6qX6MvChgjy8ZXACYGpE7oQo8uVumfRPK72ivq/DVF5RKFLP57AGKZdQ8PkP5F/ZEx0Gr5Orz51DrIZXs7r4JSZ1BVaWCdPt3O6AQNWWhrbGLiJu9gfq9YFz71jceuL5hQuJ5qohQzzIFNbRwF5MO/ejrB7kbx2aUw6grnLUKKBgwFBIvj8NZ/sXTVqASWwY1rAUocCMOunLuu/Z0eA1gnCxkm4CUUnRz OX13tFvncv/CruSrCyQ/YSJEo9OWvWXLUWFPN72nQIFFHqUyGMiGWy3IWHPbPdg/rk1ihtdZwR/OcoRZaTeb3 QwyQgrpUTk3xIrIr/4/GuuwqgYc1frCf6tWSbtOjnkuK6ZhCloQWUXicMIX9BlM0NOg1Ga x4R19YS38QdFn0O8YnnrNWUDRi2Zqw4oVuFJKWbfUmyQ3uNsOUpeRtojJDTtuULQp9MR/0jbs3Rd3wohx a3f3imMrpP1YgwOjKtoaY/jFAEnAt9pcL/81h0e5M6LwgA5JBIg/tgpVE/X5A9z/WYcPUQJf7UfZC53HISTwoYQvPUgQGR3552B qUM8HXZGcMJ6lk6LpcQAaX cas/bz9MXOneYuHvKL56qwgJZOPYHzlhgIDFW5KKKzpwy9OkmWDBukqSzV24AdRDUoS19AGO iJepD6ZPIxfHEJxMWZKnm7SZrgW3 ZMQj37WOq0xoI8vGVwAmBqRiI7Q7S1dfqqkfVP5X8EBqzf3BuTcs8KTJgmck3rkEax46D76zqSOO40fMmCP9bDl7Aw8UdCUkRT7dzugEDVlqHhK QdTBMmgdFn0O8Ynnri YULieaqIUM8yBTW0cBeTDv3o6we5G8dmlMOoK5y1D9yyphBPMQ0TWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9p0CBRR6lMhtAe 60Ni8R5z3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/GrNk981oAVnIASFRsfDKsgbNSdK0O30ZaEFlF4nDCF/QZTNDToNRmvseEdfWEt/EHRZ9DvGJ56zVlA0YtmasOKFbhSSlm31JskN7jbDlKXkbaIyQ07blC0KfTEf9I27N0Xd8KIcfmt394pjK6T9WIMDoyraGmP4xQBJwLfaXC//NYdHuTOi8I4g3FFz7kuZVRP1 QPc/1mHD1ECX 1H2QudxyEk8KGELz1IEBkd edgfqlDPB12RnDCepZOi6XEAGl/nGrP28/TFzp3mLh7yi eqsICWTj2DqLgRB9fqotSis6cMvTpJlgwbpKks1duAHUQ1KEtfQBjvoiXqQ mTymjKu6fmzkUAFejcmE/DZM4MyNPXHFIF1CPLxlcAJgakYiO0O0tXX6qpH1T V/BAas39wbk3LPCkyYJnJN65BGseOg  s6kjjuNHzJgj/Ww5ewMPFHQlJEU 3c7oBA1Zah4SvkHUwTJoHRZ9DvGJ564vmFC4nmqiFDPMgU1tHAXkw796OsHuRvHZpTDqCuctQ/csqYQTzENE1n xdNWoBJbBjWsBShwIw66cu679nR4DWCcLGSbgJRSdHP45fXe0W dy/8Ku5KsLJD9hIkSj05a9ZctRYU83vadAgUUepTIaSJyRK3kJq2892D uTWKG11nBH85yhFlpN5vf5DDJCCulROTfEisiv/j8a67CqBhzV sJ/q1ZJu06OeS4rpmEKLwX3zcJK4N8u9WE4ZBEjlbe2TeoskiRyTEzt2EGTsiAsGUpl1709Xi30zlyX6ZOM60n8Fp58PwJG2iMkNO25QtCn0xH/SNuzdF3fCiHH5rd/eKYyuk/ViDA6Mq2hpj MUAScC32lwv/zWHR7kzovCOmwIIfis8yZUT9fkD3P9Zhw9RAl/tR9kLncchJPChhC89SBAZHfnnYH6pQzwddkZwwnqWToulxABpf5xqz9vP0xc6d5i4e8ovnqrCAlk49g3e3DUSL6R7EorOnDL06SZYMG6SpLNXbgB1ENShLX0AY76Il6kPpk8jF8cQnExZkqebtJmuBbf5kxCPftY6rTGgjy8ZXACYGpuhZAavsTDSS0EXuDXFzFrFF5RKFLP57AGKZdQ8PkP5F/ZEx0Gr5Orz51DrIZXs7r4JSZ1BVaWCdPt3O6AQNWWoeEr5B1MEyaB0WfQ7xieeuL5hQuJ5qohQzzIFNbRwF5MO/ejrB7kbwACNrsh5pxeyspwRxeoDfINZ/sXTVqASWwY1rAUocCMOunLuu/Z0eA1gnCxkm4CUUnRz OX13tFvncv/CruSrCyQ/YSJEo9OWvWXLUWFPN72nQIFFHqUyGIo2ewJ/0H2fPdg/rk1ihtdZwR/OcoRZaTeb3 QwyQgrpUTk3xIrIr/4/GuuwqgYc1frCf6tWSbtOjnkuK6ZhCi8F983CSuDfLvVhOGQRI5W3tk3qLJIkclxJ7WiOk94Y5bMB8x//HSeDMjT1xxSBdQjy8ZXACYGpD QQVd7AIV20EXuDXFzFrGVJQX9LkNraMPki1m34z oZbRdZmcqCZwFXTfXGuOWCjLEPw9Kzb8yDBukqSzV24LkKDpua6mfCNhBY8exTONm53HISTwoYQhkLWcRxM7X/NUrb3vAcCEKcdSd/REy1uSmkNHzY/OufgR6hdsmZITHYee6Obz4wS1D25wMJJaqGzNb D1pUISO PSU1viDv80 3c7oBA1ZaY18nObkZb3abOTZmQWu55jVlA0YtmasOpC4FmHOqOh5/poUfZJ 7oUbaIyQ07blCRQuVKWVjKFvR83DaEgm1kTJgmck3rkEax46D76zqSOPAPJAoNl078XGAB5SF7zi3GWU3 nZeuMpPtQHbpc2GRwoULlBq54yzRc4SzLbbqjWuUxlYFhpevybedhiosHlnbFy8IfP13NHhS63JNkohAZftrZA2rAzaSPTtAKbjUoSs5qA80PqjkADNdm5pRjaBhjDpM6M8wNCFkyQr3u0ZikNomutPUIc3au5iG45UB1EEcaxTAdpqkcP7qpfoy8KGCPLxlcAJgakYiO0O0tXX6qpH1T V/BAas39wbk3LPCkyYJnJN65BGseOg  s6kjjuNHzJgj/Ww5ewMPFHQlJEU 3c7oBA1Zah4SvkHUwTJoHRZ9DvGJ564vmFC4nmqiFDPMgU1tHAXkw796OsHuRvHZpTDqCuctQCUu0qVXYu3k1n xdNWoBJbBjWsBShwIw66cu679nR4DWCcLGSbgJRSdHP45fXe0W dy/8Ku5KsLJD9hIkSj05a9ZctRYU83vadAgUUepTIZ01QC9LnapI892D uTWKG11nBH85yhFlpN5vf5DDJCCulROTfEisivGKZdQ8PkP5F/ZEx0Gr5Orz51DrIZXs7r4JSZ1BVaWCdPt3O6AQNWWhrbGLiJu9gfq9YFz71jces1ZQNGLZmrDlcHYh1MD Ko JJlSyAzzR8bQF0nk0fjnh5ciX9MX7Vq0DwCvURYviuCpk2ZPVtnj Kk9TRb3d8UUPbnAwklqoZQO2KjyRsrb9A LjIUNwutXbHPq6C9hzpaXCaqrC9OPFOdGHxJeWDlOeE2ZUpMJ8 O3veAxxk6iCByry9enawHkNpduNhmzmqfpbbnCawXpp7vWDZV8234EoZUQal6bB16mFu91m1Bkt/XQmh0dmK8s75OJRaNbW58Sjz5Xeovkpoyrun5s5FABXo3JhPw2TODMjT1xxSBdQjy8ZXACYGpGIjtDtLV1 qqR9U/lfwQGrN/cG5NyzwpMmCZyTeuQRrHjoPvrOpI47jR8yYI/1sOVpqfpDpl3zAZZTf6dl64yk 1AdulzYZHChQuUGrnjLPActMKk69aDq5TGVgWGl6/Jt52GKiweWdsXLwh8/Xc0eFLrck2SiEBl 2tkDasDNpI9O0ApuNShKzmoDzQ qOQAM12bmlGNoGGMOkzozzA0IWTJCve7RmKjVdlWWmkKhdq7mIbjlQHUQRxrFMB2mqRw/uql jLwoYI8vGVwAmBqRO6EKPLlbpn0Tyu9or6vw1ReUShSz ewMas2T3zWgBWcgBIVGx8MqyBs1J0rQ7fRloQWUXicMIX9BlM0NOg1Ga x4R19YS38QdFn0O8YnnrNWUDRi2Zqw4oVuFJKWbfUmyQ3uNsOUpeG0BdJ5NH454eXIl/TF 1atA8Ar1EWL4rgqZNmT1bZ49XorYbbu8EYlD25wMJJaqGUDtio8kbK2/QPi4yFDcLrV2xz6ugvYc6WlwmqqwvTjxTnRh8SXlg5TnhNmVKTCfPjt73gMcZOohpAvg5aV4kuyMWMyBwCiibn6W25wmsF6ae71g2VfNt BKGVEGpemwdephbvdZtQZLf10JodHZivLO TiUWjW1ufEo8 V3qL5KaMq7p bORQAV6NyYT8NkzgzI09ccUgXUI8vGVwAmBqRiI7Q7S1dfqqkfVP5X8EBqzf3BuTcs8KTJgmck3rkEax46D76zqSOO40fMmCP9bDl7Aw8UdCUkRT7dzugEDVlqHhK QdTBMmgdFn0O8Ynnri YULieaqIUM8yBTW0cBeTDv3o6we5G8AAja7IeacXuEuiuF mE/wzWf7F01agElsGNawFKHAjDrpy7rv2dHgNYJwsZJuAlFJ0c/jl9d7Rb53L/wq7kqwskP2EiRKPTlr1ly1FhTze9v1K8Md6TIYxcrL1FfKbb7z3YP65NYobXWcEfznKEWWk3m9/kMMkIK6VE5N8SKyK/ PxrrsKoGHNX6wn rVkm7To55LiumYQovBffNwkrg3y71YThkESOVt7ZN6iySJHJMTO3YQZOyICwZSmXXvT1eLfTOXJfpk4zrSfwWnnw/AkbaIyQ07blCRQuVKWVjKFvR83DaEgm1kTJgmck3rkEax46D76zqSOPAPJAoNl078XGAB5SF7zi3GWU3 nZeuMpPtQHbpc2GRwoULlBq54yzRc4SzLbbqjWuUxlYFhpevybedhiosHlnbFy8IfP13NHhS63JNkohAZftrZA2rAzaSPTtAKbjUoQWKZnoNy0VaKawhj0JazRXJbXRrzUv/3A=</body><signature>55ba41d4ebfc5110e457ed67f0ce9a21</signature></content>", "utf-8");
//			HttpUtil.doPostStream("http://localhost:8178/lotto-ticket/channel/yuanrunde/api", "123456", "test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

