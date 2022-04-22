package com.portal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.portal.controller.PortalController;
import com.portal.model.Container;
import com.portal.model.ContainerType;
import com.portal.service.ContainerService;

import reactor.core.publisher.Mono;

@SpringBootTest
class ContainerPortalApplicationTests {

	@Autowired
	private PortalController controller;

	@MockBean
	ContainerService containerService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	Container container = new Container();
	String booking = "{\"containerType\":\"REEFER\",\"containerSize\":\"40\",\\\"origin\\\":\\\"Singapore\\\",\\\"destination\\\":\\\"london\\\",\\\"quantity\\\":\\\"3\\\"}";

	@Test
	void containerAvailableCheck() throws Exception {
		container.setContainerSize(40);
		container.setContainerType(ContainerType.DRY.toString());
		Mono<Container> monoContainer = Mono.just(container);
		Mockito.when(containerService.findByContainerTypeAndContainerSize(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(monoContainer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/bookings").accept(MediaType.APPLICATION_JSON)
				.content(booking);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		System.out.println(result.getResponse());
		String expected = "{available:true}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
