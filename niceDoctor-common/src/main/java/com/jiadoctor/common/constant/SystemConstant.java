/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.constant;

/**
 * jvm 中的系统常量key
 * 
 * eg.
 * awt.toolkit=sun.awt.windows.WToolkit
 * catalina.base=D:\work\tomcat8.0
 * catalina.home=D:\work\tomcat8.0
 * catalina.useNaming=true
 * common.loader="${catalina.base}/lib","${catalina.base}/lib/*.jar","${catalina.home}/lib","${catalina.home}/lib/*.jar"
 * file.encoding=UTF-8
 * file.encoding.pkg=sun.io
 * file.separator=\
 * java.awt.graphicsenv=sun.awt.Win32GraphicsEnvironment
 * java.awt.printerjob=sun.awt.windows.WPrinterJob
 * java.class.path=D:\work\tomcat8.0\bin\bootstrap.jar;D:\work\tomcat8.0\bin\tomcat-juli.jar;C:\Program Files\Java\jdk1.7.0_79\lib\tools.jar
 * java.class.version=51.0
 * java.endorsed.dirs=D:\work\tomcat8.0\endorsed
 * java.ext.dirs=C:\Program Files\Java\jdk1.7.0_79\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
 * java.home=C:\Program Files\Java\jdk1.7.0_79\jre
 * java.io.tmpdir=C:\Users\ADMINI~1\AppData\Local\Temp\
 * java.library.path=C:\Program Files\Java\jdk1.7.0_79\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;C:\Program Files\Java\jdk1.7.0_79\jre\bin;C:\Program Files (x86)\Common Files\NetSarang;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Program Files\Java\jdk1.7.0_79\bin;C:\Program Files\Java\jdk1.7.0_79\\jre\bin;D:\work\TortoiseSVN\bin;D:\work\tomcat8.0\lib;D:\work\tomcat8.0\bin;C:\Program Files\MySQL\MySQL Server 5.5\bin;D:\work\apache-maven-3.3.3/bin;.
 * java.naming.factory.initial=org.apache.naming.java.javaURLContextFactory
 * java.naming.factory.url.pkgs=org.apache.naming
 * java.rmi.server.randomIDs=true
 * java.runtime.name=Java(TM) SE Runtime Environment
 * java.runtime.version=1.7.0_79-b15
 * java.specification.name=Java Platform API Specification
 * java.specification.vendor=Oracle Corporation
 * java.specification.version=1.7
 * java.vendor=Oracle Corporation
 * java.vendor.url=http://java.oracle.com/
 * java.vendor.url.bug=http://bugreport.sun.com/bugreport/
 * java.version=1.7.0_79
 * java.vm.info=mixed mode
 * java.vm.name=Java HotSpot(TM) 64-Bit Server VM
 * java.vm.specification.name=Java Virtual Machine Specification
 * java.vm.specification.vendor=Oracle Corporation
 * java.vm.specification.version=1.7
 * java.vm.vendor=Oracle Corporation
 * java.vm.version=24.79-b02
 * jboss.i18n.generate-proxies=true
 * line.separator=\r\n
 * os.arch=amd64
 * os.name=Windows 7
 * os.version=6.1
 * package.access=sun.,org.apache.catalina.,org.apache.coyote.,org.apache.jasper.,org.apache.tomcat.
 * package.definition=sun.,java.,org.apache.catalina.,org.apache.coyote.,org.apache.jasper.,org.apache.naming.,org.apache.tomcat.
 * path.separator=;
 * server.loader=
 * shared.loader=
 * sun.arch.data.model=64
 * sun.boot.class.path=C:\Program Files\Java\jdk1.7.0_79\jre\lib\resources.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\rt.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\sunrsasign.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jce.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.7.0_79\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.7.0_79\jre\classes
 * sun.boot.library.path=C:\Program Files\Java\jdk1.7.0_79\jre\bin
 * sun.cpu.endian=little
 * sun.cpu.isalist=amd64
 * sun.desktop=windows
 * sun.io.unicode.encoding=UnicodeLittle
 * sun.java.command=org.apache.catalina.startup.Bootstrap start
 * sun.java.launcher=SUN_STANDARD
 * sun.jnu.encoding=GBK
 * sun.management.compiler=HotSpot 64-Bit Tiered Compilers
 * sun.os.patch.level=Service Pack 1
 * tomcat.util.buf.StringCache.byte.enabled=true
 * tomcat.util.scan.StandardJarScanFilter.jarsToScan=log4j-core*.jar,log4j-taglib*.jar
 * tomcat.util.scan.StandardJarScanFilter.jarsToSkip=bootstrap.jar,commons-daemon.jar,tomcat-juli.jar,annotations-api.jar,el-api.jar,jsp-api.jar,servlet-api.jar,websocket-api.jar,catalina.jar,catalina-ant.jar,catalina-ha.jar,catalina-storeconfig.jar,catalina-tribes.jar,jasper.jar,jasper-el.jar,ecj-*.jar,tomcat-api.jar,tomcat-util.jar,tomcat-util-scan.jar,tomcat-coyote.jar,tomcat-dbcp.jar,tomcat-jni.jar,tomcat-websocket.jar,tomcat-i18n-en.jar,tomcat-i18n-es.jar,tomcat-i18n-fr.jar,tomcat-i18n-ja.jar,tomcat-juli-adapters.jar,catalina-jmx-remote.jar,catalina-ws.jar,tomcat-jdbc.jar,tools.jar,commons-beanutils*.jar,commons-codec*.jar,commons-collections*.jar,commons-dbcp*.jar,commons-digester*.jar,commons-fileupload*.jar,commons-httpclient*.jar,commons-io*.jar,commons-lang*.jar,commons-logging*.jar,commons-math*.jar,commons-pool*.jar,jstl.jar,taglibs-standard-spec-*.jar,geronimo-spec-jaxrpc*.jar,wsdl4j*.jar,ant.jar,ant-junit*.jar,aspectj*.jar,jmx.jar,h2*.jar,hibernate*.jar,httpclient*.jar,jmx-tools.jar,jta*.jar,log4j*.jar,mail*.jar,slf4j*.jar,xercesImpl.jar,xmlParserAPIs.jar,xml-apis.jar,junit.jar,junit-*.jar,ant-launcher.jar,cobertura-*.jar,asm-*.jar,dom4j-*.jar,icu4j-*.jar,jaxen-*.jar,jdom-*.jar,jetty-*.jar,oro-*.jar,servlet-api-*.jar,tagsoup-*.jar,xmlParserAPIs-*.jar,xom-*.jar
 * user.country=CN
 * user.dir=D:\work\ide\eclipse
 * user.home=C:\Users\Administrator
 * user.language=zh
 * user.name=Administrator
 * user.script=
 * user.timezone=Asia/Shanghai
 * user.variant=
 * wtp.deploy=D:\work\tomcat8.0\webapps
 * 
 * @author Panda
 * @version 1.0
 */
public class SystemConstant {

	// 系统常量
	// key of webroot`s path in jvm common
	public static final String WTP_DEPLOY= "wtp.deploy";
	public static final String CATALINA_HOME = "catalina.home";
	public static final String FILE_SEPARATOR = "file.separator";
	
}
