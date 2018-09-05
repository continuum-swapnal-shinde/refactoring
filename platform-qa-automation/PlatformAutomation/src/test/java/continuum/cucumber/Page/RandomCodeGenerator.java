package continuum.cucumber.Page;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class RandomCodeGenerator {

	public static String randomNumberGenerator(int length) {
		String Chars = "123456789";
		Random random = new Random();
		StringBuilder randomNumber = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb1.append(Chars.charAt(random.nextInt(Chars.length())));
		}
		randomNumber.append(sb1);
		return randomNumber.toString();
	}
	
	 public static String generateUUID() {
         return UUID.randomUUID().toString();
     }
	 
	 public static String generateDateTimeNowUTC() {
	        String dateTimeUtcNow = Instant.now().toString();
	        return dateTimeUtcNow;
	    }

	
	
}
