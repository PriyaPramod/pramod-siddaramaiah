package com.monefy.utils;

import java.io.File;
import java.util.HashMap;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

public class AppiumServer {

	private AppiumDriverLocalService server;

	public void startAppiumServer() {

		AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();

		if (System.getProperty("os.name").contains("Win")) {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("cmd.exe /c start cmd.exe /k appium -a 0.0.0.0 -p 4723", null,
						new File("C:\\Program Files (x86)\\nodejs\\"));
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to start the appium server on windows. ");
			}

		} else if (System.getProperty("os.name").contains("mac")) {

			// To get the available port, if APPIUM Default 4723 is used by another process
			serviceBuilder.withIPAddress("0.0.0.0");
			serviceBuilder.usingPort(4723);
			serviceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, "4723");
			serviceBuilder.withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, "4723");

			// Tell where node is installed.
			serviceBuilder.usingDriverExecutable(new File("/usr/local/bin/node"));
			// Tell where APPIUM is installed.
			serviceBuilder.withAppiumJS(new File("/usr/local/bin/appium"));
			// The XCUITest driver requires that a path to the CARTHAGE binary is in the
			// PATH variable. So setting the path
			HashMap<String, String> environment = new HashMap<String, String>();
			environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
			serviceBuilder.withEnvironment(environment);

			server = AppiumDriverLocalService.buildService(serviceBuilder);
			server.start();

			if (server.isRunning()) {
				System.out.println("Appium Server Started");
			} else {
				System.out.println("Server startup failed");
				System.exit(0);
			}
		}

	}

	public void stopAppiumServer() {
		if (System.getProperty("os.name").contains("Win")) {
			try {
				Runtime runtime = Runtime.getRuntime();
				runtime.exec("taskkill /F /IM node.exe");
				runtime.exec("taskkill /F /IM cmd.exe");
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
		} else if (System.getProperty("os.name").contains("mac")) {
			if (server.isRunning()) {
				server.stop();
			} else {
				System.out.println("Server stop failed");
				System.exit(0);
			}
		}
	}

}
