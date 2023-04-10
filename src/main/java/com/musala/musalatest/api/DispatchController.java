package com.musala.musalatest.api;

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
    public ApiResponse addDispatch(@RequestBody DispatchRequest dispatchRequest){
        return new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(),dispatchService.loadDroneForDispatch(dispatchRequest));
    }
}
