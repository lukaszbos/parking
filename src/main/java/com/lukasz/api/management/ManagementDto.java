package com.lukasz.api.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lukasz.api.client.Client;
import com.lukasz.api.parking.Parking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementDto {
    private Long billId;
    private Client client;
    private LocalDateTime enteredParkingAt;
    private LocalDateTime leftParkingAt;

    private Long ticketId;
    private Parking parking;
    @JsonProperty
    private boolean isClientAtParking;

}
