package com.nostromo.rough;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTimeStamp {
	public static void main(String[] args) {

		Date date = new Date();
		String screenshotName = date.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		System.out.println(screenshotName);
		System.out.println(date);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()).toString()
				.replace(":", "_")
				.replace(".", "_")
				.replace("-", "_")
				.replace(" ", "_") + ".jpg");
	}

}
