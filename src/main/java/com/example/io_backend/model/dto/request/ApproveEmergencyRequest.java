package com.example.io_backend.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApproveEmergencyRequest {
    @NotEmpty
    List<Integer> ambulanceIds;

    @Min(1)
    @Max(10)
    short dangerRating;
}
