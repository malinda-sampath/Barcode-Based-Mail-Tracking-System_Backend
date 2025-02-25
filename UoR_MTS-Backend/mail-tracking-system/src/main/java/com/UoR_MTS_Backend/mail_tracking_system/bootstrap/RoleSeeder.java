package com.UoR_MTS_Backend.mail_tracking_system.bootstrap;

import com.UoR_MTS_Backend.mail_tracking_system.entities.Role;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepo roleRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] {
                RoleEnum.SUPER_ADMIN,
                RoleEnum.MAIL_HANDLER,
                RoleEnum.BRANCH_MANAGER
        };

        Map<RoleEnum, String> roleDescriptionMap = Map.of(
            RoleEnum.SUPER_ADMIN, "Super Administrator role",
            RoleEnum.MAIL_HANDLER, "Mail Handler role",
            RoleEnum.BRANCH_MANAGER, "Branch Manager role"
        );

        Arrays.stream(roleNames).forEach((roleEnum -> {
            Optional<Role> optionalRole = roleRepo.findByName(roleEnum);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role role = new Role();
                role.setName(roleEnum);
                role.setDescription(roleDescriptionMap.get(roleEnum));
                roleRepo.save(role);
            });
        }));
    }
}
