import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;


public class OperationTests {
	@Test
	public void testAdd(){
		Integer result = new Integer(4);
		assertEquals(result,(Number)4);

		assertEquals(result, new Integer(4)); //true
		//assertEquals(result, (new ChainInteger).add(1,3))
		assertEquals(new Integer(11), (new Addition()).apply(5,6)); //true
	}

	@Test
	public void testSubtr(){
		Integer result = new Integer(12);

		assertEquals(result, (new Subtraction()).apply(15,3)); //true
	}

	@Test
	public void testMultiply(){
		Integer result = new Integer(72);

		assertEquals(result, (new Multiplication()).apply(9,8)); //true
	}

	@Test
	public void testStep(){
		Integer result = new Integer(25);

		assertEquals(result, (new Step()).apply(5,2)); //true
	}

	@Test
	public void testAlwaysPassed(){
		assertTrue(true);
	}
}