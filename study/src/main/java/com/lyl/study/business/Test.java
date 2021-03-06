package com.lyl.study.business;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;

public class Test extends JPanel {
 
	private static final long serialVersionUID = 1L;
 
	private JPanel webBrowserPanel;
 
	private JWebBrowser webBrowser;
 
	 private String url;
 
	public Test(String url) {
		super(new BorderLayout());
		this.url = url;
		webBrowserPanel = new JPanel(new BorderLayout());
		webBrowser = new JWebBrowser();
		webBrowser.navigate(url);
		webBrowser.setButtonBarVisible(false);
		webBrowser.setMenuBarVisible(false);
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(false);
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		add(webBrowserPanel, BorderLayout.CENTER);
		// webBrowser.executeJavascript("javascrpit:window.location.href='http://www.baidu.com'");
		 webBrowser.executeJavascript("alert('haha')"); //执行Js代码
	}
 
	public static void main(String[] args) {
		final String url = "http://www.baidu.com";
		final String title = "DJ NativeSwiting Test";
		UIUtils.setPreferredLookAndFeel();
		NativeInterface.open();
 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(title);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new Test(url), BorderLayout.CENTER);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
		NativeInterface.runEventPump();
	}
 
}