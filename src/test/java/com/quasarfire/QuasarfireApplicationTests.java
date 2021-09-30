package com.quasarfire;

import com.quasarfire.interfaces.QuasarFireInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuasarfireApplicationTests {

	@Autowired
	QuasarFireInterface quasarfire;

	@Test
	public void testGetPosition() {
		double distances[] = {100,200,500};
		String expected = "Coordenada X = -237.0214340768837, Coordenada Y = -136.73985079932635";

		String result = quasarfire.getLocation(distances);

		Assert.assertEquals(result, expected);
	}

	@Test
	public void testGetPositionTwoDinstancesFail()  {
		double distances[] = {100,200};
		String expected = "No se puede determinar la posicion de la nave";

		String result = quasarfire.getLocation(distances);

		Assert.assertEquals(result, expected);
	}

	@Test
	public void testGetPositionFourDistancesFail(){
		double distances[] = {100,200,300,-500};
		String expected = "No se puede determinar la posicion de la nave";

		String result = quasarfire.getLocation(distances);

		Assert.assertEquals(result, expected);
	}

	@Test
	public void testGetPositionNullDistances(){
		double distances[] = null;
		String expected = "No se puede determinar la posicion de la nave";

		String result = quasarfire.getLocation(distances);

		Assert.assertEquals(result, expected);
	}
}
