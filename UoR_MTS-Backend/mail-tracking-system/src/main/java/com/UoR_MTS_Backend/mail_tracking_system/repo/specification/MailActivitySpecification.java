package com.UoR_MTS_Backend.mail_tracking_system.repo.specification;

import com.UoR_MTS_Backend.mail_tracking_system.model.MailActivity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MailActivitySpecification {

    public static Specification<MailActivity> filterBy(
            String userName,
            String activityType,
            String branchName,
            String senderName,
            String receiverName
    ) {
        return new Specification<MailActivity>() {
            @Override
            public Predicate toPredicate(Root<MailActivity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                System.out.println("Hello World");
                // Filter by userName
                if (userName != null && !userName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("userName"), "%" + userName + "%"));
                }

                // Filter by activityType
                if (activityType != null && !activityType.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("activityType"), activityType));
                }

                // Filter by branchName
                if (branchName != null && !branchName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("branchName"), "%" + branchName + "%"));
                }

                // Filter by senderName
                if (senderName != null && !senderName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("senderName"), "%" + senderName + "%"));
                }

                // Filter by receiverName
                if (receiverName != null && !receiverName.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("receiverName"), "%" + receiverName + "%"));
                }

                // Combine all predicates with AND
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
