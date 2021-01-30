package caloriescalculator.osgi;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.eclipse.osgi.framework.internal.core.Constants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
/***************************************************************************************************
*
* COMPANION CODE FOR THE BOOK �Component Oriented Development & Assembly � CODA Using Java�
* 
* @author � Piram Manickam, Sangeetha S, Subrahmanya S V
* @see - http://www.codabook.com
* 
* <br><br><b>CODE CONTRIBUTORS</b> � <p>- Vishal Verma, Shikhar Johari, Soumya Bardhan, Rohit Jain,
* 										  Karthika Nair, Vibhuti Pithwa, Vaasavi Lakshmi</p>
****************************************************************************************************/
public class Launcher {

    public static void main(String[] args) {
        try {
            FrameworkFactory fwFactory = ServiceLoader
                            .load(FrameworkFactory.class).iterator().next();

            Map<String, String> configMap = new HashMap<String, String>();

            configMap.put("org.osgi.framework.storage.clean", "onFirstInit");

            Framework framework = fwFactory.newFramework(configMap);
            framework.init();
            BundleContext bndlCtxt = framework.getBundleContext();
            Bundle mainBundle = null;

            List<Bundle> bundleList = new LinkedList<Bundle>();

            File folder = new File(".");
            // System.out.println(folder.getAbsolutePath());
            for (File file : folder.listFiles()) {
                // System.out.println(file.getName());
                if (file.getName().endsWith(".jar")&&!file.getName().contains("org.eclipse.osgi_")) {
                    System.out.println("\t".concat(file.getName()));
                    Bundle bundle = bndlCtxt.installBundle(file.toURI()
                                    .toString());
                    bundleList.add(bundle);

                    if (bundle.getHeaders().get("Main-Class") != null) {
                            mainBundle = bundle;
                    }
                }
            }

            System.out.println("framework start");
            framework.start();

            for (Bundle bundle : bundleList) {
                if (bundle.getHeaders().get(Constants.FRAGMENT_HOST) == null) {
                    try {
                        System.out.println(bundle.getSymbolicName());
                        bundle.start();
                    } catch (BundleException be) {
                        be.printStackTrace();
                    }
                } else {
                    System.out.println("2");
                }
            }
            System.out.println("framework stop");
            framework.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
