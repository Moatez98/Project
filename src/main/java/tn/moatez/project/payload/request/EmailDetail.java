package tn.moatez.project.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDetail {
    @NotNull
    private String recipient;
    private String subject;


}
