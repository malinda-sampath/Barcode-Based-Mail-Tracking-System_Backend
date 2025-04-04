package com.UoR_MTS_Backend.mail_tracking_system.services.IMPL;

import com.UoR_MTS_Backend.mail_tracking_system.controllers.WebSocketController;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse.WCMailHandlerUpdateDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.RegisterUserDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.request.MailHandlerRequestDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailHandlerResponseDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse.WCBranchUpdateDTO;
import com.UoR_MTS_Backend.mail_tracking_system.dtos.websocketResponse.WCMailHandlerUpdateDTO;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Branch;
import com.UoR_MTS_Backend.mail_tracking_system.entities.Role;
import com.UoR_MTS_Backend.mail_tracking_system.entities.RoleEnum;
import com.UoR_MTS_Backend.mail_tracking_system.entities.User;
import com.UoR_MTS_Backend.mail_tracking_system.exception.AlreadyExistsException;
import com.UoR_MTS_Backend.mail_tracking_system.exception.UserNotFoundException;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.BranchRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.RoleRepo;
import com.UoR_MTS_Backend.mail_tracking_system.repositories.UserRepo;
import com.UoR_MTS_Backend.mail_tracking_system.services.MailHandlerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MailHandlerServiceIMPL implements MailHandlerService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final WebSocketController webSocketController;

    @Override
    public String saveMailHandler(MailHandlerRequestDTO mailHandlerRequestDTO) {

        if (userRepo.findByEmail(mailHandlerRequestDTO.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email is already in use.");
        }

        User user = modelMapper.map(mailHandlerRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(mailHandlerRequestDTO.getPassword()));
        user.setRole(roleRepo.findByName(RoleEnum.MAIL_HANDLER).orElseThrow(() -> new RuntimeException("Role not found")));
        user.setBranchCode("BR-0001");

        try {
            userRepo.save(user);

            // Send the updated mail handler to the web socket
            MailHandlerResponseDTO mailHandlerResponseDTO = modelMapper.map(user, MailHandlerResponseDTO.class);
            WCMailHandlerUpdateDTO wcMailHandlerUpdateDTO = new WCMailHandlerUpdateDTO("save",mailHandlerResponseDTO);
            webSocketController.sendMailHandlerUpdate(wcMailHandlerUpdateDTO);

            return "Mail Handler created successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error creating Mail Handler" + e.getMessage());
        }
    }

    @Override
    public String updateMailHandler(long id, MailHandlerRequestDTO mailHandlerRequestDTO) {

//        MailHandler mailHandler = mailHandlerRepo.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("No MailHandler found with the provided ID: " + id));
//
//
//        if (!mailHandler.getEmail().equals(mailHandlerDTO.getEmail()) && mailHandlerRepo.existsByEmail(mailHandlerDTO.getEmail())) {
//            throw new MailHandlerException("Email is already in use.");
//        }
//
//
//        if (!mailHandler.getUserName().equals(mailHandlerDTO.getUserName()) && mailHandlerRepo.existsByUserName(mailHandlerDTO.getUserName())) {
//            throw new MailHandlerException("Username is already in use.");
//        }
//
//
//        mailHandler.setName(mailHandlerDTO.getName());
//        mailHandler.setUserName(mailHandlerDTO.getUserName());
//        mailHandler.setEmail(mailHandlerDTO.getEmail());
//        mailHandler.setContact(mailHandlerDTO.getContact());
//        mailHandler.setPassword(mailHandlerDTO.getPassword());
//        mailHandler.setRole(mailHandlerDTO.getRole());
//        mailHandler.setUpdateDate(LocalDateTime.now());
//
//
//
//
//        try {
//            mailHandlerRepo.save(mailHandler);
//
//            return "Mail Handler updated successfully.";
//
//
//        }catch(Exception e){
//
//            throw new RuntimeException("Error Update Mail Handler"+e.getMessage());
//        }
        return null;
    }

    @Override
    public String deleteMailHandler(String id) {
        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException("No MailHandler found with the provided id.");
        } else {

            Optional<User> user = userRepo.findById(id);

            try {
                userRepo.deleteById(id);

                MailHandlerResponseDTO mailHandlerResponseDTO = modelMapper.map(user, MailHandlerResponseDTO.class);
                WCMailHandlerUpdateDTO wcMailHandlerUpdateDTO = new WCMailHandlerUpdateDTO("delete",mailHandlerResponseDTO);
                webSocketController.sendMailHandlerUpdate(wcMailHandlerUpdateDTO);

                return "Mail Handler deleted successfully.";
            }catch(Exception e){
                throw new RuntimeException("Error Deleting Mail Handler"+e.getMessage());
            }
        }

    }

    @Override
    public List<MailHandlerResponseDTO> getAllMailHandlers() {
        List<User> users = userRepo.findAllByRole_Name(RoleEnum.MAIL_HANDLER);

        if (users.isEmpty()) {
            throw new UserNotFoundException("No Mail Handlers found.");
        }

        return users.stream()
                .map(user -> modelMapper.map(user, MailHandlerResponseDTO.class))
                .toList();
    }
}
