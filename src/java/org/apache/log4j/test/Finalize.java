

package org.apache.log4j.test;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class Finalize {

  static Logger CAT = Logger.getLogger(Finalize.class.getName());

  public
  static
  void main(String argv[]) {

    if(argv.length == 1)
      init(argv[0]);
    else
      Usage("Wrong number of arguments.");

    test();
  }

  static
  void Usage(String msg) {
    System.err.println(msg);
    System.err.println( "Usage: java " + Finalize.class.getName() +
			" configFile");
    System.exit(1);
  }


  static
  void init(String configFile) {
    PropertyConfigurator.configure(configFile);
  }

  static
  void test() {
    int i = -1;

    InputStreamReader in = new InputStreamReader(System.in);
    Logger root = Logger.getRootLogger();

    System.out.println("Type 'q' to quit");
    int j = 0;
    while (true) {
      System.gc();
      try {i = in.read(); }
      catch(Exception e) { return; }
      System.gc();
      System.out.println("Read ["+i+"].");
      if(i == -1)
	break;
      else if(i == 'q')
	break;
      else
	root.debug("Hello " + (++j));
    }

    //foo(root);
    root.removeAllAppenders();
    System.gc(); delay(3000);
    System.gc(); delay(3000);
    System.gc(); delay(3000);  System.gc();
  }

  static
  void foo(Logger cat) {
    Enumeration enumaration = cat.getAllAppenders();
    while(enumaration != null && enumaration.hasMoreElements()) {
      ((org.apache.log4j.Appender) enumaration.nextElement()).close();
    }
  }



  static
  void delay(int amount) {
    try {
      Thread.currentThread().sleep(amount);
    }
    catch(Exception e) {}
  }

}