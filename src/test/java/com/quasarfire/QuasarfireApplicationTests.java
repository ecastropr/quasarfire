package com.quasarfire;

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

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuasarfireApplicationTests {

	@Autowired
	QuasarFireInterface quasarfire;

	@Autowired
	ObtainMessage obtainMessage;

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

	@Test
	public void testGetMessageSuccess(){
		String msg1 []= {"este","","un","","secreto"};
		String msg2 []= {"","es","un","",""};
		String msg3 []= {"este","","","mensaje","secreto"};
		String expected = "este es un mensaje secreto";

		List<List<String>> sendingMessages = new ArrayList<>();

		sendingMessages.add(Arrays.stream(msg1).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg2).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg3).collect(Collectors.toList()));

		Assert.assertEquals(expected,obtainMessage.getMessage(sendingMessages));

	}

	@Test(expected = ResponseStatusException.class)
	public void testGetMessageBadRequest(){
		String msg1 []= {"este","","un","secreto"};
		String msg2 []= {"este","","un","","secreto"};
		String msg3 []= {"este","","un","","secreto"};

		List<List<String>> sendingMessages = new ArrayList<>();
		sendingMessages.add(Arrays.stream(msg1).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg2).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg3).collect(Collectors.toList()));

		obtainMessage.getMessage(sendingMessages);
	}

	@Test(expected = ResponseStatusException.class)
	public void testGetMessageDataInsufficient(){
		String msg1 []= {"este","","","","secreto"};
		String msg2 []= {"","es","","",""};
		String msg3 []= {"este","","","mensaje","secreto"};
		String expected = "este es un mensaje secreto";

		List<List<String>> sendingMessages = new ArrayList<>();

		sendingMessages.add(Arrays.stream(msg1).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg2).collect(Collectors.toList()));
		sendingMessages.add(Arrays.stream(msg3).collect(Collectors.toList()));

		Assert.assertEquals(expected,obtainMessage.getMessage(sendingMessages));

	}
}
