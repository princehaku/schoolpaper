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
 *  Created on : 2010-11-3, 22:29:53
 *  Author     : princehaku
 */
package net.techest.schoolpaper.util;

import android.util.Log;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.techest.schoolpaper.paper.Paper;
import net.techest.schoolpaper.paper.PaperType;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author princehaku
 */
public class XmlToPapers {

    
    private ArrayList<Paper> papers = new ArrayList();

    private String url;
    
    public XmlToPapers(String url) {
        this.url=url;
    }

    public ArrayList<Paper> parse() throws Exception {
        papers = new ArrayList();
        DocumentBuilderFactory docBuilderFactory = null;
        DocumentBuilder docBuilder = null;
        Document doc = null;
        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            //xml file 放到 assets目录中的
            doc = (Document) docBuilder.parse(this.url);
            //root element
            Element root = (Element) doc.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("paper");
            Paper paper = new Paper();
            try {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    paper = new Paper();
                    for (int j = 0; j < nodeList.item(i).getChildNodes().getLength(); j++) {
                        Node nd = nodeList.item(i).getChildNodes().item(j);
                        if (nd.getNodeName().equals("id")) {
                            String value = nd.getTextContent();
                            paper.setId(Integer.parseInt(value));
                        }
                        if (nd.getNodeName().equals("title")) {
                            String value = nd.getTextContent();
                            paper.setTitle(value);
                        }
                        if (nd.getNodeName().equals("x")) {
                            String value = nd.getTextContent();
                            paper.setX(Integer.parseInt(value));
                        }
                        if (nd.getNodeName().equals("y")) {
                            String value = nd.getTextContent();
                            paper.setY(Integer.parseInt(value));
                        }
                        if (nd.getNodeName().equals("type")) {
                            String value = nd.getTextContent();
                            paper.setType(PaperType.parseType(Integer.parseInt(value)));
                        }
                        if (nd.getNodeName().equals("picname")) {
                            String value = nd.getTextContent();
                            paper.setImageName(value);
                        }
                        if (nd.getNodeName().equals("content")) {
                            String value = nd.getTextContent();
                            paper.setContent(value);
                        }
                        if (nd.getNodeName().equals("paperdate")) {
                            String value = nd.getTextContent();
                            paper.setPaperDate(value);
                        }
                        if (nd.getNodeName().equals("addtime")) {
                            String value = nd.getTextContent();
                            paper.setAddDate(value);
                        }
                        if (nd.getNodeName().equals("deepth")) {
                            String value = nd.getTextContent();
                            paper.setDeepth(Integer.parseInt(value));
                        }
                    }
                    papers.add(paper);
                    Log.i("", "one Paper Added");
                }
            } catch (Exception ex) {
                Log.i("", "parsing faild :" + ex.getMessage());
            }
        } catch (Exception ex) {
            Log.i("", "connect server faild :" + ex.getMessage());
            throw ex;
        }
        return papers;
    }
}
