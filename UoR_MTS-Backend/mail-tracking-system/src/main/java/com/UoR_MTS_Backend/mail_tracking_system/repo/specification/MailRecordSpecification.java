package com.UoR_MTS_Backend.mail_tracking_system.repo.specification;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailRecord;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MailRecordSpecification {
    public static Specification<MailRecord> filterBy(
            String senderName,
            String receiverName,
            String mailType,
            String trackingNumber,
            String branchName) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by senderName
            if (senderName != null && !senderName.isEmpty()) {
                System.out.println("DEBUG: Adding senderName filter: " + senderName);
                predicates.add(criteriaBuilder.like(root.get("senderName"), "%" + senderName + "%"));
            }
            if (receiverName != null && !receiverName.isEmpty()) {
                System.out.println("DEBUG: Adding receiverName filter: " + receiverName);
                predicates.add(criteriaBuilder.like(root.get("receiverName"), "%" + receiverName + "%"));
            }
            if (mailType != null && !mailType.isEmpty()) {
                System.out.println("DEBUG: Adding mailType filter: " + mailType);
                predicates.add(criteriaBuilder.like(root.get("mailType"), "%" + mailType + "%"));
            }
           if (trackingNumber != null && !trackingNumber.isEmpty()) {
                System.out.println("DEBUG: Adding trackingNumber filter: " + trackingNumber);
                predicates.add(criteriaBuilder.like(root.get("trackingNumber"), "%" + trackingNumber + "%"));
            }
            if (branchName != null && !branchName.isEmpty()) {
                System.out.println("DEBUG: Adding branchName filter: " + branchName);
                predicates.add(criteriaBuilder.like(root.get("branchName"), "%" + branchName + "%"));
            }

            // Log total predicates
            System.out.println("DEBUG: Total predicates added = " + predicates.size());
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
