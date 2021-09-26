package ru.diasoft.micro.domain.securityprofiledto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecuritySearchParamDto implements Serializable {
    private List<Long> securityIds;
    private List<String> isinCodes;
}
