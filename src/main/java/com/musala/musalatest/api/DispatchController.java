package com.musala.musalatest.api;

import com.musala.musalatest.Exceptions.WeightOverloadException;
import com.musala.musalatest.business.dto.DispatchRequest;
import com.musala.musalatest.service.DispatchService;
import com.musala.musalatest.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dispatch")
@RequiredArgsConstructor
public class DispatchController {

    private final DispatchService dispatchService;

    @PostMapping("/process")
    public ApiResponse addDispatch(@RequestBody DispatchRequest dispatchRequest) throws WeightOverloadException {
        return new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(),dispatchService.loadDroneForDispatch(dispatchRequest));
    }

    @PutMapping("/confirm-load")
    public ApiResponse confirmLoadForDispatch(@RequestParam("dispatch-reference") String dispatchReference,
                                              @RequestParam("drone-serialNumber") String droneSerialNumber)  {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),dispatchService.confirmLoadForDispatch(dispatchReference,droneSerialNumber));
    }

    @PutMapping("/deploy")
    public ApiResponse deployDroneForDispatch(@RequestParam("dispatch-reference") String dispatchReference,
                                              @RequestParam("drone-serialNumber") String droneSerialNumber)  {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),dispatchService.deployDroneForDispatch(dispatchReference,droneSerialNumber));
    }

    @PutMapping("/cancel")
    public ApiResponse cancelDispatch(@RequestParam("dispatch-reference") String dispatchReference,
                                              @RequestParam("drone-serialNumber") String droneSerialNumber)  {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),dispatchService.cancelDispatch(dispatchReference,droneSerialNumber));
    }

    @PutMapping("/confirm-delivery")
    public ApiResponse confirmDelivery(@RequestParam("dispatch-reference") String dispatchReference,
                                              @RequestParam("drone-serialNumber") String droneSerialNumber)  {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),dispatchService.confirmDelivery(dispatchReference,droneSerialNumber));
    }

    @PutMapping("/complete")
    public ApiResponse completeDispatch(@RequestParam("dispatch-reference") String dispatchReference,
                                              @RequestParam("drone-serialNumber") String droneSerialNumber)  {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),dispatchService.completeDispatch(dispatchReference,droneSerialNumber));
    }
}
