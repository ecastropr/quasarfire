package com.quasarfire;

import com.quasarfire.entities.Satelite;
import com.quasarfire.entities.ShipPosition;
import com.quasarfire.entities.TopSecretResponse;
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
		expected.setxPosition(-237.02);
		expected.setyPosition(-136.74);

		ShipPosition result = obtainLocation.getLocation(distances);

		System.out.println(result.getxPosition());
		System.out.println(result.getyPosition());

		Assert.assertTrue(expected.getxPosition()== result.getxPosition() && expected.getyPosition() == result.getyPosition());
	}

	@Test(expected = ResponseStatusException.class)
	public void testGetPositionTwoDinstancesFail()  {
		double[] distances = {100,200};
		String expected = "No hay información suficiente para procesar la posicion y el mensaje";

		ShipPosition result = obtainLocation.getLocation(distances);
	}

	@Test(expected = ResponseStatusException.class)
	public void testGetPositionFourDistancesFail(){
		double[] distances = {100,200,300,-500};
		String expected = "No hay información suficiente para procesar la posicion y el mensaje";

		ShipPosition result = obtainLocation.getLocation(distances);

	}

	@Test(expected = ResponseStatusException.class)
	public void testGetPositionNullDistances(){

		obtainLocation.getLocation(null);

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

		obtainMessage.getMessage(sendingMessages);

	}

	@Test
	public void testTopSecretSuccess(){
		List<Satelite> satelites = new ArrayList<>();
		TopSecretResponse response;
		TopSecretResponse expected;
		String[] msg1 = {"este","","un","","secreto"};
		String[] msg2 = {"","es","","",""};
		String[] msg3 = {"este","","","mensaje","secreto"};
		ShipPosition position = new ShipPosition();
		position.setxPosition(-4.32);
		position.setyPosition(-116.47);

		expected = TopSecretResponse.builder().position(position).message("este es un mensaje secreto").build();
		satelites.add(Satelite.builder().name("kenovi").distance(10.3).message(List.of(msg1)).build());
		satelites.add(Satelite.builder().name("skywalker").distance(103).message(List.of(msg2)).build());
		satelites.add(Satelite.builder().name("sato").distance(400).message(List.of(msg3)).build());

		response = quasarfire.getInfoTopSecret(satelites);

		Assert.assertTrue(response.getMessage().equals(expected.getMessage())
				&& response.getPosition().getxPosition() == expected.getPosition().getxPosition()
				&& response.getPosition().getyPosition() == expected.getPosition().getyPosition());

	}

	@Test(expected = ResponseStatusException.class)
	public void testTopSecretFail(){
		List<Satelite> satelites = new ArrayList<>();
		ShipPosition position = new ShipPosition();
		TopSecretResponse expected;
		String[] msg1 = {"este","","","","secreto"};
		String[] msg2 = {"","es","","",""};
		String[] msg3 = {"este","","","mensaje","secreto"};

		position.setxPosition(-385.82);
		position.setyPosition(-177.15);
		satelites.add(Satelite.builder().name("kenovi").distance(10.3).message(List.of(msg1)).build());
		satelites.add(Satelite.builder().name("skywalker").distance(103).message(List.of(msg2)).build());
		satelites.add(Satelite.builder().name("sato").distance(400).message(List.of(msg3)).build());

		quasarfire.getInfoTopSecret(satelites);

	}

}
