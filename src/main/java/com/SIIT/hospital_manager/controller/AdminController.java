package com.siit.hospital_manager.controller;


import com.siit.hospital_manager.model.Admin;
import com.siit.hospital_manager.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/mvc/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create")
    @Operation(summary = "This is for creating users", description = "This is the long description",
            responses = {@ApiResponse(responseCode = "201", description = "All went well"),
                    @ApiResponse(responseCode = "400", description = "Some field is invalid or missing")}
    )
    public ResponseEntity createUser(@Valid @RequestBody Admin user){
        return ResponseEntity.status(201).body(Map.of("Id", adminService.createUser(user)));
    }

}