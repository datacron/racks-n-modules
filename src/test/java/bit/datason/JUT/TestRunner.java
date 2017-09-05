package bit.datason.JUT;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	
	public static void main(String[] args) {
		
		Result result = JUnitCore.runClasses(TestRack.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		System.out.println("WAS RESULT SUCCESSFUL ?: " + result.wasSuccessful());
	}

}
