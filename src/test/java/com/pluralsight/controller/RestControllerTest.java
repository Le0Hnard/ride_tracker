package com.pluralsight.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout = 3000)
	public void testCreateRide() {
		RestTemplate restTemplate = new RestTemplate();
		Ride ride = new Ride();

		ride.setName("Demolition Derby");
		ride.setDuration(180);

//		restTemplate.put("http://localhost:8080/ride_tracker/ride", ride);
		ride = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride, Ride.class); // post returns an object

		System.out.println("Ride: " + ride);
	}

	@Test(timeout=3000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}

	@Test(timeout = 3000)
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/1", Ride.class);

		System.out.println("Ride name: " + ride.getName());
	}

	@Test(timeout = 3000)
	public void testUpdateRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/1", Ride.class);

		ride.setDuration(ride.getDuration() + 10);

		restTemplate.put("http://localhost:8080/ride_tracker/ride", ride);

		System.out.println("Ride name: " + ride.getName());
	}

	@Test(timeout = 3000)
	public void testBatchUpdate() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject("http://localhost:8080/ride_tracker/batch", Object.class);
	}

	@Test(timeout = 3000)
	public void testDelete() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.delete("http://localhost:8080/ride_tracker/delete/5");
	}

	@Test(timeout = 3000)
	public void testException() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject("http://localhost:8080/ride_tracker/test", Ride.class);
	}
}
