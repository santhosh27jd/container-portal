package com.portal.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.portal.model.Booking;
import com.portal.model.Container;
import com.portal.repository.ContainerRepository;
import com.portal.exception.ServiceException;
import com.portal.model.Response;

import reactor.core.publisher.Mono;

/**
 * Class ContainerService
 * 
 * @author santhoshkumardurairaj
 *
 */
@Service
public class ContainerService {

	/**
	 * ContainerRepository object injected
	 */
	@Autowired
	private ContainerRepository containerRepository;

	/**
	 * RestTemplate object injected
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * External URL from yaml file
	 */
	@Value("${url}")
	private String EXTERNAL_API; // URL FROM YML FILE

	/**
	 * External API call
	 * @param booking
	 * @return
	 */
	public Response callExternalApi(Booking booking) {
		try {
			// CHECK AVAILABLE SPACE
			Response res = new Response();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Booking> entity = new HttpEntity<Booking>(booking, httpHeaders);
			String response = restTemplate.exchange(EXTERNAL_API, HttpMethod.POST, entity, String.class).getBody();
			int value = 0;
			JSONObject json;
			json = new JSONObject(response);
			value = Integer.parseInt(String.valueOf(json.get("availableSpace")));
			// AVAILABLE RETURN TRUE / FALSE
			if (value == 0) {
				res.setAvailable(false);
			} else {
				res.setAvailable(true);
			}
			return res;
		} catch (Exception ex) {
			throw new ServiceException();
		}
	}

	/**
	 * save
	 * 
	 * @param container
	 * @return
	 */
	public Mono<com.portal.model.Container> save(Container container) {
		return containerRepository.save(container);
	}

	/**
	 * findByContainerTypeAndContainerSize
	 * 
	 * @param containerType
	 * @param containerSize
	 * @return
	 */
	public Mono<Container> findByContainerTypeAndContainerSize(String containerType, int containerSize) {
		// TODO Auto-generated method stub
		return containerRepository.findByContainerTypeAndContainerSize(containerType, containerSize);
	}

	/**
	 * deleteById
	 * 
	 * @param id
	 */
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		containerRepository.deleteById(id);
	}

}
