package com.company.view;

import javax.swing.SwingUtilities;

public class TimeManagement {
	public static void main(String[] args){
		// run my program in a special thread
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				AuthenticationFrame authenticationFrame = new AuthenticationFrame();
			}	
		});
	}
}
