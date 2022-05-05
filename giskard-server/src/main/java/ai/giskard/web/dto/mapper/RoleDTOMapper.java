package ai.giskard.web.dto.mapper;

import ai.giskard.domain.Role;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleDTOMapper {
    Set<Role> map(Set<String> value) {
        return value.stream().map(roleName -> {
            Role role = new Role();
            role.setName(roleName);
            return role;
        }).collect(Collectors.toSet());
    }

}