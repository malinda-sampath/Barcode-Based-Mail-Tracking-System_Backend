package com.UoR_MTS_Backend.mail_tracking_system.repo.specification;

import com.UoR_MTS_Backend.mail_tracking_system.model.DailyMail;
import com.UoR_MTS_Backend.mail_tracking_system.model.MailActivity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailActivity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DailyMailSpecification {
    public static Specification<DailyMail> filterBy(
            String senderName,
            String receiverName,
            String mailType,
            String trackingNumber,
            String branchName
    ) {
        return new Specification<DailyMail>() {
            @Override
            public Predicate toPredicate(Root<DailyMail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                System.out.println("Hello World");
                // Filter by senderName
                if (senderName != null && !senderName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("senderName"), "%" + senderName + "%"));
                }

                // Filter by receiverName
                if (receiverName != null && !receiverName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("receiverName"), "%" + receiverName + "%"));
                }

                // Filter by mailType
                if (mailType != null && !mailType.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("mailType"), "%" + mailType + "%"));
                }

                // Filter by trackingNumber
                if (trackingNumber != null && !trackingNumber.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("trackingNumber"), "%" + trackingNumber + "%"));
                }

                // Filter by branchName
                if (branchName != null && !branchName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("branchName"), "%" + branchName + "%"));
                }

                // Combine all predicates with AND
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}


