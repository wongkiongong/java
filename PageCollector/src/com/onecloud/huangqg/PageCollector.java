package com.onecloud.huangqg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;

enum ItemType{
	IMAGE,
	BUY,
	SELL,
	MIDDLE,
	HIGHESTMID,
	LOWESTMID,
	TREND,
	OPERATION
};

/**
* @author www.baizeju.com
*/
public class PageCollector {
    private static String ENCODE = "utf-8";
    private static void message( String szMsg ) {
        try{ System.out.println(new String(szMsg.getBytes(ENCODE), System.getProperty("file.encoding"))); }     catch(Exception e ){}
    }
    public static String openFile( String szFileName ) {
        try {
            BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)),    ENCODE) );
            String szContent="";
            String szTemp;
            
            while ( (szTemp = bis.readLine()) != null) {
                szContent+=szTemp+"\n";
            }
            bis.close();
            return szContent;
        }
        catch( Exception e ) {
            return "";
        }
    }
    
   public static Node getNodes(Node node){
	   NodeList children = node.getChildren();
	   for(int i=0; i< children.size();i++){
		   Node child = (Node) children.elementAt(i);
		   if(child.getChildren() == null){
			   String text = child.getText();
			   if("品种".equals(text)){
				   return child;
			   }
			   else if(child.getNextSibling() == null)
			   {
				   continue;
			   }
			   else
			   {
				   return getNodes((Node) child.getNextSibling()); 
			   }
			   
		   }
		   else
		   {
			   getNodes(child.getFirstChild());
		   }
		   
	   }
	   return children.elementAt(0);
   }
    
   public static void main(String[] args) {
        
        try{
            Parser parser = new Parser( (HttpURLConnection) (new URL("http://www.icbc.com.cn/ICBCDynamicSite/Charts/GoldTendencyPicture.aspx")).openConnection() );
            
            for (NodeIterator i = parser.elements (); i.hasMoreNodes(); ) {
                Node node = i.nextNode();
                message("getText:"+node.getText());
                message("getPlainText:"+node.toPlainTextString());
                message("toHtml:"+node.toHtml());
//                message("toHtml(true):"+node.toHtml(true));
//                message("toHtml(false):"+node.toHtml(false));
                message("toString:"+node.toString());
                message("=================================================");
            }             
            
//	        NodeFilter filter = new TagNameFilter ("table");
//	        NodeList nodes = parser.extractAllNodesThatMatch(filter); 
//	        Node result = getNodes(nodes.elementAt(0));
//	        message("target is:" + result.getText());
            
//            NodeFilter innerFilter = new TagNameFilter ("table");
            NodeFilter filter = new HasAttributeFilter( "id", "TABLE1" );
            NodeList tables = parser.extractAllNodesThatMatch(filter); 
            
            int size = tables.size();
            Node table = ((Node) tables.elementAt(0));
            Node tbodyNode = (Node) (table.getFirstChild().getNextSibling());
            String body = tbodyNode.toHtml();
            String text = tbodyNode.getText();	
            if(tbodyNode != null) {
            	NodeList trsNodes = tbodyNode.getChildren();
                for (int i = 0; i < trsNodes.size(); i++) {
                    Node trnode = (Node) trsNodes.elementAt(i);
                    if("品种".equals(trnode.getFirstChild().getText())){
                    	NodeList tdNodes = trnode.getChildren();
                    	for(int j=0; j < tdNodes.size(); j ++){
                    		message(j + tdNodes.elementAt(j).getText());
                            message("=================================================");
                    	}
                    	 
                    }
                }
            } 
           
        }
        catch( Exception e ) {     
            System.out.println( "Exception:"+e );
        }
    }
}
