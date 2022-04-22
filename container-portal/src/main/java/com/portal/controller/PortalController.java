package com.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.model.Container;
import com.portal.exception.NoContainerException;
import com.portal.exception.ParameterException;
import com.portal.exception.ServiceException;
import com.portal.model.Booking;
import com.portal.model.Response;
import com.portal.service.ContainerService;

import reactor.core.publisher.Mono;

import com.portal.model.ContainerType;

/**
 * Class PortalController
 * @author santhoshkumardurairaj
 *
 */
@RestController
@RequestMapping("/api/bookings")
public class PortalController {
	
	/**
	 * ContainerService Object injection
	 */
	@Autowired
	private ContainerService containerService;
	
	/**
	 * Post Mapping 
	 * @param booking (Booking details)
	 * @return Response as JSON
	 */
	@PostMapping
	public Response submit(@RequestBody Booking booking) {
		try {
			// VALIDATING PARAM
			if ((booking.getContainerSize() == 20 || booking.getContainerSize() == 40)
					&& (booking.getContainerType().equals(ContainerType.DRY.toString())
							|| booking.getContainerType().equals(ContainerType.REEFER.toString()))
					&& (booking.getOrigin().length() >= 5 && booking.getOrigin().length() <= 20)
					&& (booking.getDestination().length() >= 5 && booking.getDestination().length() <= 20)
					&& (booking.getQuantity() >= 1 && booking.getQuantity() <= 100)) {
				
				// CHECK CONTAINER AVAILABILTY FOR BOOKING
				Mono<Container> containerMono = containerService.findByContainerTypeAndContainerSize(
						booking.getContainerType(), booking.getContainerSize());
				Container availCont = containerMono.share().block();
				if (availCont != null) {
					// CONTAINER AVAILABLE FOR BOOKING, CALLING EXTERNAL API
					Response response = containerService.callExternalApi(booking);
					return response;
				}else {
					//HANDLING NO CONTAINER EXCEPTION
					throw new NoContainerException();
				}
			}else {
				//HANDLING PARAM EXCEPTION
				throw new ParameterException();
			}
			
		}catch(Exception ex) {
			if(ex instanceof ParameterException) {
				throw new ParameterException();
			}else if(ex instanceof NoContainerException) {
				throw new NoContainerException();
			}else {
				// HANDLING SERVIC EXCEPTION
				throw new ServiceException();
			}
		}
	}
}
