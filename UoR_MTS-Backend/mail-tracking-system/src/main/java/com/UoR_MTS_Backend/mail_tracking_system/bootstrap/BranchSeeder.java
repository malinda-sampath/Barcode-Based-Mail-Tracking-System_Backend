package com.UoR_MTS_Backend.mail_tracking_system.bootstrap;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.BranchDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BranchSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final ModelMapper modelMapper;
    private final BranchRepo branchRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createBranch();
    }

    private void createBranch() {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchCode("BR-0001");
        branchDTO.setBranchName("Main Branch");
        branchDTO.setBranchDescription("General Admin Branch");
        branchDTO.setInsertDate(LocalDateTime.now());

        Optional<Branch> optionalBranch = Optional.ofNullable(branchRepo.findByBranchCode("BR-0001"));

        if (optionalBranch.isPresent()) {
            return;
        }

        Branch branch = modelMapper.map(branchDTO, Branch.class);

        try {
            branchRepo.save(branch);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
