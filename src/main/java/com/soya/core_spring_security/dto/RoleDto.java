
package com.soya.core_spring_security.dto;

import com.soya.core_spring_security.entity.Account;
import com.soya.core_spring_security.entity.Resources;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto{

    private String id;
    private String roleName;
    private String roleDesc;
    private Account account;
    private Set<Resources> resourcesSet = new LinkedHashSet<>();

}


