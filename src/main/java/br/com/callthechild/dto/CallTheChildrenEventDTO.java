package br.com.callthechild.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallTheChildrenEventDTO {
    private Integer ageGroup;
    private Integer numberChild;
}
