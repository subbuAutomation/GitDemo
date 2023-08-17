package common;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;

public class JiraUtil {
	static String path = System.getProperty("user.dir");
	public static String[] cmdArray = new String[11];
	
	public static void JiraIntegration() {
        String path1 = Paths.get(path.toString(), "lib", "JiraIntegration.jar").toString();
        File jarFile = new File(path1);
        String JiraIntegrationParameters = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"JiraIntegrationParameters");
        String[] JiraPara = JiraIntegrationParameters.split("[,]", 0);
/*        for(String myStr: JiraPara) {
           System.out.println(myStr);
        }*/
         cmdArray[0] = "java";
	     cmdArray[1] = "-jar";
	     cmdArray[2] = jarFile + "";
	     cmdArray[3] = JiraPara[0];     // JiraUrl
	     cmdArray[4] = JiraPara[1];     // Username
	     cmdArray[5] = JiraPara[2];     //Password or Token
	     cmdArray[6] = JiraPara[3];     // ProjectKey
	     cmdArray[7] = JiraPara[4];	    // IssueType
	//     cmdArray[8] = "Summary2";        // Summary
	//     cmdArray[9] = "Description";     // Description
	     cmdArray[10] = JiraPara[5];    // ReporterName
	     
     try {	
    	  
    	// To execute Jar file with arguments     
			 Process proc = Runtime.getRuntime().exec(cmdArray);
			 proc.waitFor();
		// To get the responce after executing jar file	
			 InputStream in = proc.getInputStream();
			 InputStream err = proc.getErrorStream();
			 
			 byte b[]=new byte[in.available()];
			    in.read(b,0,b.length);
			    System.out.println(new String(b));

			    byte c[]=new byte[err.available()];
			    err.read(c,0,c.length);
			    System.out.println(new String(c));
			    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("#########The Jar file Exception is ### "+ e);
			e.printStackTrace();
		}
        } 


}
