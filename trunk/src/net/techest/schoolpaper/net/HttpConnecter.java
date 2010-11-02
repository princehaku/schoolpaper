/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : 2010-11-2, 9:00:56
 *  Author     : princehaku
 */

package net.techest.schoolpaper.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载网页内容
 * 
 * @author princehaku
 */
public class HttpConnecter {
	/**
	 * url
	 *
	 * @param url
	 *            提交地址
	 * @param encode
	 *            编码
	 */
	@SuppressWarnings("finally")
	public String get(String url, String encode) throws Exception {

		String line = "";

		String content = "";

		HttpURLConnection httpConn = null;

		try {
			URL turl = new URL(url);
			// System.out.println(url);
			httpConn = (HttpURLConnection) turl.openConnection();
			httpConn.setRequestMethod("GET");
                        httpConn.connect();
			InputStream uurl=null;
			uurl = httpConn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(uurl,encode));
			while (line != null) {
				line = br.readLine();
				if (line   !=   null)
					content = content.toString() + line.toString() + "\n";
			}
			// 关闭连接
			httpConn.disconnect();
			return content;

		} catch (Exception e) {
			// 关闭连接
			httpConn.disconnect();
			throw e;
                }
	}
}
