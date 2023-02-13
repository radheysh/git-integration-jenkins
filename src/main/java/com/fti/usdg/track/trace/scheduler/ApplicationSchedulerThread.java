/**
 * 
 */
package com.fti.usdg.track.trace.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Anup Kumar Gupta
 *
 */
public class ApplicationSchedulerThread extends Thread {
	BufferedReader bf;

	public ApplicationSchedulerThread(InputStream input) {
		bf = new BufferedReader(new InputStreamReader(input));
	}
	public void run() {
		String line;
		try {
			line = bf.readLine();
			while (line != null) {
				System.out.println(line);
				line = bf.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
