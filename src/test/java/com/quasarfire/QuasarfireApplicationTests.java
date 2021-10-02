package com.quasarfire;

import com.quasarfire.entities.ShipPosition;
import com.quasarfire.interfaces.ObtainLocation;
import com.quasarfire.interfaces.ObtainMessage;
import com.quasarfire.interfaces.QuasarFireInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuasarfireApplicationTests {

	@Autowired
	QuasarFireInterface quasarfire;

	@Autowired
	ObtainMessage obtainMessage;

	@Autowired
	ObtainLocation obtainLocation;

	@Test
	public void testGetPosition() {
		double [] distances = {100,200,500};
		ShipPosition expected = new ShipPosition();
		expected.setxPosition(-237.0214340768837);
		expected.setyPosition(-136.73985079932635);

		ShipPosition result = obtainLocation.getLocation(distances);

		System.out.println(result.getxPosition());
		System.out.println(expected.getxPosition());

		Assert.assertTrue("Los valores coinciden",expected.getxPosition()== result.getxPosition() && expected.getyPosition()== result.getyPosition());
	}

	@Test(expected = ResponseStatusException.class)
	public void testGetPositionTwoDinstancesFail()  {
		double[] distances = {100,200};
		String expected = "No se puede determinar la posicion de la nave";

		ShipPosition result = obtainLocation.getLocation(distances);
	}

	@Test(expected = ResponseStatusException.class)
	public void testGetPositionFourDistancesFail(){
		double[] distances = {100,200,300,-500};
		String expected = "No se puede determinar la posicion de la nave";

		ShipPosition result = obtainLocation.getLocation(distances);

	}

	@Test(expected = ResponseStatusException.class)
	public void testGetPositionNullDistances(){
		String expected = "No se puede determinar la posicion de la nave";

		ShipPosition result = obtainLocation.getLocation(null);

		Assert.assertEquals(result, expected);
	}

	@Test
	public void testGetMessageSuccess(){
		String[] msg1 = {"este","","un","","secreto"};
		String[] msg2 = {"","es","un","",""};
		String[] msg3 = {"este","","","mensaje","secreto"};
		String expected = "este es un mensaje secreto";

		List<List<String>> sendingMessages = new ArrayList<>();

		sendingMessages.add(Arrays.stream(msg1).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg2).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg3).collect(Collectors.toList()));

		Assert.assertEquals(expected,obtainMessage.getMessage(sendingMessages));

	}

	@Test(expected = ResponseStatusException.class)
	public void testGetMessageBadRequest(){
		String[] msg1 = {"este","","un","secreto"};
		String[] msg2 = {"este","","un","","secreto"};
		String[] msg3 = {"este","","un","","secreto"};

		List<List<String>> sendingMessages = new ArrayList<>();
		sendingMessages.add(Arrays.stream(msg1).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg2).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg3).collect(Collectors.toList()));

		obtainMessage.getMessage(sendingMessages);
	}

	@Test(expected = ResponseStatusException.class)
	public void testGetMessageDataInsufficient(){
		String[] msg1 = {"este","","","","secreto"};
		String[] msg2 = {"","es","","",""};
		String[] msg3 = {"este","","","mensaje","secreto"};
		String expected = "este es un mensaje secreto";

		List<List<String>> sendingMessages = new ArrayList<>();

		sendingMessages.add(Arrays.stream(msg1).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg2).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg3).collect(Collectors.toList()));

		Assert.assertEquals(expected,obtainMessage.getMessage(sendingMessages));

	}
}
